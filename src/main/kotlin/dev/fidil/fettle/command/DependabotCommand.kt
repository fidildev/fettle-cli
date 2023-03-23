package dev.fidil.fettle.command

import dev.fidil.fettle.handler.FettleHandler

class DependabotCommand(override val handler: FettleHandler) :
    GitHubRepoSubCommand("dependabot", "Validate dependabot is active") {

    override fun processCommand(): CommandResult {
        return handler.dependabot(org, repo, branch)
    }
}