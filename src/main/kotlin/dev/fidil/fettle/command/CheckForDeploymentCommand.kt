package dev.fidil.fettle.command

import dev.fidil.fettle.handler.FettleHandler

class CheckForDeploymentCommand(override val handler: FettleHandler) :
    GitHubRepoSubCommand("checkForDeployments", "Check if the repo uses deployment pipelines") {

    override fun processCommand(): CommandResult {
        return handler.deploymentPipelines(org, repo, branch)
    }
}
