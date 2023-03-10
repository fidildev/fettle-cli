@file:OptIn(ExperimentalCli::class)

package dev.fidil.fettle

import kotlinx.cli.*
import org.kohsuke.github.GitHubBuilder

class BranchProtectionCommand : Subcommand("branchProtection", "Validate Branch protection is enabled") {
    private val ghToken = System.getenv("GH_TOKEN")
    private val ghUser = System.getenv("GH_USER")
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
        result = branchProtectionEnabled(org, repo, branch, ghToken, ghUser)
        println("Branch Protection Enabled: $result")
    }


    private fun branchProtectionEnabled(org: String, repo: String, branch: String, accessToken: String, githubUser: String): Boolean {
        val github = GitHubBuilder().withOAuthToken(accessToken, githubUser).build()
        return github.getRepository("$org/$repo").getBranch(branch).isProtected
    }
}