package dev.fidil.fettle.command

import org.kohsuke.github.GitHubBuilder

class StaticCodeAnalysisCommand(private val user: String, private val token: String) :
    GitHubRepoSubCommand("staticCodeAnalysis", "Validate static code analysis is active") {

    private var result: Boolean = false
    override fun execute() {
        result = staticCodeAnalysisActive(org, repo, token, user)
        println("Static Code Analysis Active: $result")
    }

    private fun staticCodeAnalysisActive(org: String, repo: String, accessToken: String, githubUser: String): Boolean {
        val github = GitHubBuilder().withOAuthToken(accessToken, githubUser).build()
        return try {
            val hooks = github.getRepository("$org/$repo").hooks
            if (hooks.isNotEmpty()) {
                for (hook in hooks) {
                    val hookUrl = hook.config.entries.find { it.key.toString() == "url" }
                    if (hookUrl?.value != null && hookUrl.value!!.contains("codacy")) {
                        return true
                    }
                }
            }
            false
        } catch (e: Exception) {
            false
        }
    }
}
