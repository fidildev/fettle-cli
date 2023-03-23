package dev.fidil.fettle.command

data class FettleContext(
    val repoUser: String,
    val repoToken: String
) {

    var commandMap = mutableMapOf<String, FettleCommand>()
}
