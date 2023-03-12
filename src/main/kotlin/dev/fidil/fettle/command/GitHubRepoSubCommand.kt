package dev.fidil.fettle.command

import kotlinx.cli.*

@OptIn(ExperimentalCli::class)
abstract class GitHubRepoSubCommand(name: String, actionDescription: String) : Subcommand(name, actionDescription) {
    val org by option(
        ArgType.String, "organization", "o", "The github organization"
    ).required()
    val repo by option(
        ArgType.String, "repo", "r", "The github repo to validate"
    ).required()
    val branch by option(
        ArgType.String, "branch", "b", "The github branch to validate"
    ).default("main")
}