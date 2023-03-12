package dev.fidil.fettle.command

import org.kohsuke.github.GitHubBuilder

class DependabotCommand(private val user: String, private val token: String) :
    GitHubRepoSubCommand("dependabot", "Validate dependabot is active") {

    private var result: Boolean = false
    override fun execute() {
        result = dependabotActive(org, repo, token, user)
        println("Dependabot Active: $result")
    }

    private fun dependabotActive(org: String, repo: String, accessToken: String, githubUser: String): Boolean {
        val github = GitHubBuilder().withOAuthToken(accessToken, githubUser).build()
        return try {
            github.getRepository("$org/$repo").getFileContent("/.github/dependabot.yml")
            true
        } catch (e: Exception) {
            false
        }

    }
}