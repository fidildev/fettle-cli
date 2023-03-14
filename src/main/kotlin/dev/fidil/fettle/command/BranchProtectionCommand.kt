package dev.fidil.fettle.command

import dev.fidil.fettle.handler.FettleHandler

class BranchProtectionCommand(override val handler: FettleHandler) :
    GitHubRepoSubCommand("branchProtection", "Validate Branch protection is enabled") {

    override fun processCommand(): CommandResult {
        return handler.branchProtections(org, repo, branch)
    }
}