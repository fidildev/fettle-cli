package dev.fidil.fettle.handler

import dev.fidil.fettle.command.CommandResult
import dev.fidil.fettle.command.FettleContext
import org.kohsuke.github.GitHub
import org.kohsuke.github.GitHubBuilder

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

    override fun score(org: String, repo: String, branch: String): CommandResult {
        var count = 0.0
        if (this.branchProtections(org, repo, branch) == CommandResult.Passed(passed)) {
            count += 1.0
        }

        if (this.dependabot(org, repo, branch) == CommandResult.Passed(passed)) {
            count += 1.0
        }

        if (this.codeCoverage(org, repo, branch) == CommandResult.Passed(passed)) {
            count += 1.0
        }

        if (this.deploymentPipelines(org, repo, branch) == CommandResult.Passed(passed)) {
            count += 1.0
        }

        if (this.staticAnalysis(org, repo, branch) == CommandResult.Passed(passed)) {
            count += 1.0
        }

        return when (count / context.commandMap.size) {
            in 90.0..100.0 -> CommandResult.Score("A")
            in 80.0..89.9 -> CommandResult.Score("B")
            in 70.0..79.9 -> CommandResult.Score("C")
            in 60.0..69.9 -> CommandResult.Score("D")
            else -> CommandResult.Score("F")
        }
    }
}
