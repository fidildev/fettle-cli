@file:OptIn(ExperimentalCli::class)

package dev.fidil.fettle

import kotlinx.cli.*
import org.kohsuke.github.GitHubBuilder
import kotlin.system.exitProcess

class BranchProtectionCommand(private val user:String, private val token:String) : Subcommand("branchProtection", "Validate Branch protection is enabled") {

    private val org by option(
        ArgType.String, "organization", "o", "The github organization"
    ).required()
    private val repo by option(
        ArgType.String, "repo", "r", "The github repo to validate"
    ).required()
    private val branch by option(
        ArgType.String, "branch", "b", "The github branch to validate"
    ).default("main")

    private var result: Boolean = false
    override fun execute() {
        result = branchProtectionEnabled(org, repo, branch, token, user)
        println("Branch Protection Enabled: $result")
    }


    private fun branchProtectionEnabled(org: String, repo: String, branch: String, accessToken: String, githubUser: String): Boolean {
        val github = GitHubBuilder().withOAuthToken(accessToken, githubUser).build()
        return github.getRepository("$org/$repo").getBranch(branch).isProtected
    }
}