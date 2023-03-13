package dev.fidil.fettle.command

import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
abstract class FettleCommand(name: String, actionDescription: String) : Subcommand(name, actionDescription) {

    abstract val context: CommandContext
    override fun execute() {
        when (val result = processCommand(context)) {
            is CommandResult.Passed -> println(result.message)
            is CommandResult.Failed -> println(result.message)
            is CommandResult.Score -> println("Fettle Score: ${result.score}")
        }
    }

    abstract fun processCommand(context: CommandContext): CommandResult
}

sealed class CommandResult {
    data class Passed(val message: String) : CommandResult()
    data class Failed(val message: String) : CommandResult()
    data class Score(val score: String) : CommandResult()
}

enum class FettleScore { A, B, C, D, F }