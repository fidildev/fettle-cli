package dev.fidil.fettle.command

import org.kohsuke.github.GitHubBuilder

class CheckForDeploymentCommand(private val user: String, private val token: String) :
    GitHubRepoSubCommand("checkForDeployments", "Check if the repo uses deployment pipelines") {

    private var result: Boolean = false

    override fun execute() {
        result = deploymentPipelinesUsed(org, repo, token, user)
        println("Deployment Pipelines Found: $result")
    }

    private fun deploymentPipelinesUsed(
        org: String,
        repo: String,
        accessToken: String,
        githubUser: String
    ): Boolean {
        val github = GitHubBuilder().withOAuthToken(accessToken, githubUser).build()
        return github.getRepository("$org/$repo").listWorkflows().toList().isNotEmpty()
    }
}
