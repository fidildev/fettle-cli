package dev.fidil.fettle.command

import dev.fidil.fettle.handler.FettleHandler

class DeploymentFrequencyCommand(override val handler: FettleHandler) :
    GitHubRepoSubCommand("deploymentFrequency", "Checks the deployment frequency via GitHub releases") {

    override fun processCommand(): CommandResult {
        return handler.deploymentFrequency(org, repo, branch)
    }
}
