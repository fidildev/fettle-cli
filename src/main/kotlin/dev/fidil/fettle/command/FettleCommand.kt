package dev.fidil.fettle.command

import dev.fidil.fettle.handler.FettleHandler
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
abstract class FettleCommand(name: String, actionDescription: String) : Subcommand(name, actionDescription) {

    abstract val handler: FettleHandler
    override fun execute() {
        when (val result = processCommand()) {
            is CommandResult.Passed -> println(result.message)
            is CommandResult.Failed -> println(result.message)
            is CommandResult.Score -> println("Fettle Score: ${result.score}")
        }
    }

    abstract fun processCommand(): CommandResult
}

sealed class CommandResult {
    data class Passed(val message: String) : CommandResult()
    data class Failed(val message: String) : CommandResult()
    data class Score(val score: String) : CommandResult()
}