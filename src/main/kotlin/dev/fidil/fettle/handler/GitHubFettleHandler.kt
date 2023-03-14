package dev.fidil.fettle.handler

import dev.fidil.fettle.command.CommandResult
import dev.fidil.fettle.command.FettleContext
import org.kohsuke.github.GitHub
import org.kohsuke.github.GitHubBuilder

class GitHubFettleHandler(override val context: FettleContext) : FettleHandler {

    private val api: GitHub = GitHubBuilder().withOAuthToken(context.repoToken, context.repoUser).build()
    override fun branchProtections(org: String, repo: String, branch: String): CommandResult {
        return if (api.getRepository("$org/$repo").getBranch(branch).isProtected) {
            CommandResult.Passed("PASS")
        } else {
            CommandResult.Failed("FAIL")
        }
    }

    override fun dependabot(org: String, repo: String, branch: String): CommandResult {
        return try {
            api.getRepository("$org/$repo").getFileContent("/.github/dependabot.yml")
            CommandResult.Passed("PASS")
        } catch (e: Exception) {
            CommandResult.Failed("FAIL")
        }
    }

    override fun score(org: String, repo: String, branch: String): CommandResult {

        var count = 0.0
        if (this.branchProtections(org, repo, branch) == CommandResult.Passed("PASSED")) {
            count += 1.0
        }

        if (this.dependabot(org, repo, branch) == CommandResult.Passed("PASSED")) {
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

