package dev.fidil.fettle.command

import dev.fidil.fettle.handler.FettleHandler

class ScoreCommand(override val handler: FettleHandler) :
    GitHubRepoSubCommand("score", "Executes all registered fettle functions and provides a grade (A,B,C,D,F)") {

    override fun processCommand(): CommandResult {
        return handler.score(org, repo, branch)
    }
}
