package dev.fidil.fettle.command

import dev.fidil.fettle.handler.FettleHandler

class OrgScoreCommand(override val handler: FettleHandler) :
    GitHubOrgSubCommand("orgScore", "Executes all registered fettle functions on all repos in an org and provides a grade (A,B,C,D,F)") {

    override fun processCommand(): CommandResult {
        return handler.orgScore(org)
    }
}
