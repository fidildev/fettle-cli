package dev.fidil.fettle.command

data class CommandContext(
    val repoUser: String,
    val repoToken: String,
) {

    var commandMap = mutableMapOf<String, FettleCommand>()
}
