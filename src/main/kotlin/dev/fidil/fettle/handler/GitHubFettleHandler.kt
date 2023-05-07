package dev.fidil.fettle.handler

import dev.fidil.fettle.command.CommandResult
import dev.fidil.fettle.command.FettleContext
import dev.fidil.fettle.config.FettleFunction
import org.kohsuke.github.GitHub
import org.kohsuke.github.GitHubBuilder
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.reflections.util.ConfigurationBuilder
import java.io.IOException
import java.lang.reflect.InvocationTargetException

class GitHubFettleHandler(override val context: FettleContext) : FettleHandler {

    private val passed = "PASSED"
    private val failed = "FAILED"

    private val api: GitHub = GitHubBuilder().withOAuthToken(context.repoToken, context.repoUser).build()

    override fun branchProtections(org: String, repo: String, branch: String): CommandResult {
        return if (api.getRepository("$org/$repo").getBranch(branch).isProtected) {
            CommandResult.Passed(passed)
        } else {
            CommandResult.Failed(failed)
        }
    }

    override fun dependabot(org: String, repo: String, branch: String): CommandResult {
        return try {
            api.getRepository("$org/$repo").getFileContent("/.github/dependabot.yml")
            CommandResult.Passed(passed)
        } catch (e: Exception) {
            CommandResult.Failed(failed)
        }
    }

    override fun deploymentPipelines(org: String, repo: String, branch: String): CommandResult {
        return if (api.getRepository("$org/$repo").listWorkflows().toList().isNotEmpty()) {
            CommandResult.Passed(passed)
        } else {
            CommandResult.Failed(failed)
        }
    }

    override fun staticAnalysis(org: String, repo: String, branch: String): CommandResult {
        return try {
            val hooks = api.getRepository("$org/$repo").hooks
            if (hooks.isNotEmpty()) {
                for (hook in hooks) {
                    val hookUrl = hook.config.entries.find { it.key.toString() == "url" }
                    if ((hookUrl?.value != null) && hookUrl.value!!.contains("codacy")) {
                        return CommandResult.Passed(passed)
                    }
                }
            }
            CommandResult.Failed(failed)
        } catch (e: Exception) {
            CommandResult.Failed(failed)
        }
    }

    override fun codeCoverage(org: String, repo: String, branch: String): CommandResult {
        var searchResults = api.searchContent().repo("$org/$repo").filename("build.gradle.kts").list().toList()
        if (searchResults.isEmpty()) {
            searchResults = api.searchContent().repo("$org/$repo").filename("build.gradle").list().toList()
        }
        for (result in searchResults) {
            val content = result.read().bufferedReader().use { it.readText() }
            if (content.contains("apply plugin: 'jacoco'") || content.contains("apply plugin: \"jacoco\"")) {
                return CommandResult.Passed(passed)
            }
        }
        return CommandResult.Failed(failed)
    }

    override fun readme(org: String, repo: String, branch: String): CommandResult {
        val repository = api.getRepository("$org/$repo")
        val readmeLocations = arrayOf("README.md", "/docs/README.md", "/.github/README.md")

        for (location in readmeLocations) {
            try {
                repository.getFileContent(location)
                return CommandResult.Passed(passed)
            } catch (ignore: IOException) {}
        }

        return CommandResult.Passed(failed)
    }

    override fun score(org: String, repo: String): CommandResult {
        var count = 0.0

        val reflections = Reflections(
            ConfigurationBuilder()
                .setUrls(ClassLoader.getSystemClassLoader().getResource(""))
                .setScanners(Scanners.MethodsAnnotated)
        )
        val annotatedMethods = reflections.getMethodsAnnotatedWith(FettleFunction::class.java)
        println(repo)
        for (method in annotatedMethods) {
            val ffName = method.getDeclaredAnnotation(FettleFunction::class.java).name
            val ffFailMessage = method.getDeclaredAnnotation(FettleFunction::class.java).failMessage
            val result = try {
                method.invoke(this, org, repo, "main")
            } catch (e: InvocationTargetException) {
                method.invoke(this, org, repo, "master")
            }
            if (result == CommandResult.Passed(passed)) {
                println("\u2705 $ffName")
                count += 1.0
            } else {
                println("\u274C $ffName: $ffFailMessage")
            }
        }

        return when (val percentage = count / (context.commandMap.size - 1) * 100) {
            in 90.0..100.0 -> CommandResult.Score("A", percentage)
            in 80.0..89.9 -> CommandResult.Score("B", percentage)
            in 70.0..79.9 -> CommandResult.Score("C", percentage)
            in 60.0..69.9 -> CommandResult.Score("D", percentage)
            else -> CommandResult.Score("F", percentage)
        }
    }

    override fun orgScore(org: String): CommandResult {
        var orgScore = 0.0
        val repositories = api.getOrganization(org).repositories
        repositories.forEach {
            val score = this.score(org, it.value.name) as CommandResult.Score
            orgScore += score.percentage
        }

        return when (orgScore / repositories.size) {
            in 90.0..100.0 -> CommandResult.OrgScore("A")
            in 80.0..89.9 -> CommandResult.OrgScore("B")
            in 70.0..79.9 -> CommandResult.OrgScore("C")
            in 60.0..69.9 -> CommandResult.OrgScore("D")
            else -> CommandResult.OrgScore("F")
        }
    }
}
