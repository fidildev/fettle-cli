@file:OptIn(ExperimentalCli::class)

package dev.fidil.fettle.command

import kotlinx.cli.*
import org.kohsuke.github.GitHubBuilder

class BranchProtectionCommand(override val context: CommandContext) :
    GitHubRepoSubCommand("branchProtection", "Validate Branch protection is enabled") {

    override fun processCommand(context: CommandContext): CommandResult {
        val github = GitHubBuilder().withOAuthToken(context.repoToken, context.repoUser).build()
        return if (github.getRepository("$org/$repo").getBranch(branch).isProtected) {
            CommandResult.Passed("PASS")
        } else {
            CommandResult.Failed("FAIL")
        }
    }
}