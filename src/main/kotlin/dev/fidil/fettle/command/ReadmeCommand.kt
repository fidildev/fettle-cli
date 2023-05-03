package dev.fidil.fettle.command

import dev.fidil.fettle.handler.FettleHandler

class ReadmeCommand(override val handler: FettleHandler) :
    GitHubRepoSubCommand("readme", "Validate Readme exists in the repo") {

    override fun processCommand(): CommandResult {
        return handler.readme(org, repo, branch)
    }
}
