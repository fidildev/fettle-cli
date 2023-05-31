package dev.fidil.fettle.command

import dev.fidil.fettle.handler.FettleHandler

class CodeCoverageCommand(override val handler: FettleHandler) :
    GitHubRepoSubCommand("codeCoverage", "Validate Code Coverage is calculated") {

    override fun processCommand(): CommandResult {
        return handler.codeCoverage(org, repo, branch)
    }
}
