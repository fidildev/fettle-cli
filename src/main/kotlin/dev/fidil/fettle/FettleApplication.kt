@file:OptIn(ExperimentalCli::class)

package dev.fidil.fettle

import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    val ghToken = System.getenv("GH_TOKEN")
    val ghUser = System.getenv("GH_USER")
    if (ghToken == null || ghUser == null) {
        println("A valid GitHub user and token are required to be in your environment. Example \nexport GH_TOKEN=sometoken\nexport GH_USER=myuser")
        exitProcess(1)
    }

    val parser = ArgParser("fettle-cli")
    parser.subcommands(BranchProtectionCommand(ghUser, ghToken))
    parser.parse(args)

    println("╭ᥥ╮(´• ᴗ •`˵)╭ᥥ╮")

}


