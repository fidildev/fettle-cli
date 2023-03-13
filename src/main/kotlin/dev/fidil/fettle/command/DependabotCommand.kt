package dev.fidil.fettle.command

import org.kohsuke.github.GitHubBuilder

class DependabotCommand(override val context: CommandContext) :
    GitHubRepoSubCommand("dependabot", "Validate dependabot is active") {

    override fun processCommand(context: CommandContext): CommandResult {
        val github = GitHubBuilder().withOAuthToken(context.repoToken, context.repoUser).build()
        return try {
            github.getRepository("$org/$repo").getFileContent("/.github/dependabot.yml")
            CommandResult.Passed("PASS")
        } catch (e: Exception) {
            CommandResult.Failed("FAIL")
        }
    }
}