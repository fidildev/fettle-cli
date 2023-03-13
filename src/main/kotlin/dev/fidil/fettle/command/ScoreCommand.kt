package dev.fidil.fettle.command

class ScoreCommand(override val context: CommandContext) :
    GitHubRepoSubCommand("score", "Executes all registered fettle functions and provides a grade (A,B,C,D,F)") {

    override fun processCommand(context: CommandContext): CommandResult {

        val count = context.commandMap.filterKeys {
            it != "ScoreCommand"
        }.map { (key, value) ->
            when (value.processCommand(context)) {
                is CommandResult.Passed -> 1
                is CommandResult.Failed -> 0
                is CommandResult.Score -> 0
            }
        }.reduce { acc, i -> acc + i }

        return when (count.toDouble() / context.commandMap.size) {
            in 90.0..100.0 -> CommandResult.Score("A")
            in 80.0..89.9 -> CommandResult.Score("B")
            in 70.0..79.9 -> CommandResult.Score("C")
            in 60.0..69.9 -> CommandResult.Score("D")
            else -> CommandResult.Score("F")
        }
    }
}
