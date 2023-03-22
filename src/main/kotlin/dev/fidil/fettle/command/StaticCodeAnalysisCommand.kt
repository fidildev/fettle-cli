package dev.fidil.fettle.command

import dev.fidil.fettle.handler.FettleHandler

class StaticCodeAnalysisCommand(override val handler: FettleHandler) :
    GitHubRepoSubCommand("staticCodeAnalysis", "Validate static code analysis is active") {

    override fun processCommand(): CommandResult {
        return handler.staticAnalysis(org, repo, branch)
    }
}
