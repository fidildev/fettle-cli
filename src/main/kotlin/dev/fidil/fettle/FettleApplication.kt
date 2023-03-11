@file:OptIn(ExperimentalCli::class)

package dev.fidil.fettle

import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli


fun main(args: Array<String>) {
    val parser = ArgParser("fettle-cli")
    parser.subcommands(BranchProtectionCommand(), DependabotCommand())
    parser.parse(args)

    println("╭ᥥ╮(´• ᴗ •`˵)╭ᥥ╮")

}


