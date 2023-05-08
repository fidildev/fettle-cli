package dev.fidil.fettle.command

import dev.fidil.fettle.handler.FettleHandler
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import kotlin.math.roundToInt

@OptIn(ExperimentalCli::class)
abstract class FettleCommand(name: String, actionDescription: String) : Subcommand(name, actionDescription) {

    abstract val handler: FettleHandler
    override fun execute() {
        when (val result = processCommand()) {
            is CommandResult.Grade -> println("Grade: ${result.grade}")
            is CommandResult.Passed -> println(result.message)
            is CommandResult.Failed -> println(result.message)
            is CommandResult.Score -> println("Fettle Score: ${result.score} (${result.percentage.roundToInt()}%)")
            is CommandResult.OrgScore -> println("Fettle Org Score: ${result.orgScore}")
        }
    }

    abstract fun processCommand(): CommandResult
}

enum class LetterGrade {
    A, B, C, D, F
}

sealed class CommandResult {
    data class Grade(val grade: LetterGrade) : CommandResult()
    data class Passed(val message: String) : CommandResult()
    data class Failed(val message: String) : CommandResult()
    data class Score(val score: String, val percentage: Double) : CommandResult()

    data class OrgScore(val orgScore: String) : CommandResult()
}
