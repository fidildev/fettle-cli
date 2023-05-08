package dev.fidil.fettle.command

import dev.fidil.fettle.handler.FettleHandler

class DefaultBranchNameCommand(override val handler: FettleHandler) :
    GitHubRepoSubCommand("defaultBranchName", "Validates the default branch is not master") {

    override fun processCommand(): CommandResult {
        return handler.defaultBranchName(org, repo, branch)
    }
}
