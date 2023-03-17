package dev.fidil.fettle.command

import org.kohsuke.github.GitHubBuilder

class CodeCoverageCommand(private val user: String, private val token: String) :
    GitHubRepoSubCommand("codeCoverage", "Validate Code Coverage is Calculated") {

    private var result: Boolean = false

    override fun execute() {
        result = codeCoverageExists(org, repo, token, user)
        println("Code Coverage is Calculated: $result")
    }

    private fun codeCoverageExists(
        org: String,
        repo: String,
        accessToken: String,
        githubUser: String
    ): Boolean {
        val github = GitHubBuilder().withOAuthToken(accessToken, githubUser).build()
        var searchResults = github.searchContent().repo("$org/$repo").filename("build.gradle.kts").list().toList()
        if (searchResults.isEmpty()) {
            searchResults = github.searchContent().repo("$org/$repo").filename("build.gradle").list().toList()
        }
        for (result in searchResults) {
            val content = result.read().bufferedReader().use { it.readText() }
            if (content.contains("apply plugin: 'jacoco'") || content.contains("apply plugin: \"jacoco\"")) {
                return true
            }
        }
        return false
    }
}
