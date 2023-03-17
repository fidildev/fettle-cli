
package dev.fidil.fettle.command

import org.kohsuke.github.GitHubBuilder

class BranchProtectionCommand(private val user: String, private val token: String) :
    GitHubRepoSubCommand("branchProtection", "Validate Branch protection is enabled") {

    private var result: Boolean = false

    override fun execute() {
        result = branchProtectionEnabled(org, repo, branch, token, user)
        println("Branch Protection Enabled: $result")
    }

    private fun branchProtectionEnabled(
        org: String,
        repo: String,
        branch: String,
        accessToken: String,
        githubUser: String
    ): Boolean {
        val github = GitHubBuilder().withOAuthToken(accessToken, githubUser).build()
        return github.getRepository("$org/$repo").getBranch(branch).isProtected
    }
}
