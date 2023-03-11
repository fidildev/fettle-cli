package dev.fidil.fettle

import kotlinx.cli.ArgType
import kotlinx.cli.Subcommand
import kotlinx.cli.required
import org.kohsuke.github.GitHubBuilder

class DependabotCommand : Subcommand("dependabot", "Validate dependabot is active") {

    private val ghToken = System.getenv("GH_TOKEN")
    private val ghUser = System.getenv("GH_USER")
    private val org by option(
        ArgType.String, "organization", "o", "The github organization"
    ).required()
    private val repo by option(
        ArgType.String, "repo", "r", "The github repo to validate"
    ).required()

    private var result: Boolean = false
    override fun execute() {
        result = dependabotActive(org, repo, ghToken, ghUser)
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