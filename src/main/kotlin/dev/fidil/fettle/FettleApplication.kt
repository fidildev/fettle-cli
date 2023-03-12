@file:OptIn(ExperimentalCli::class)

package dev.fidil.fettle

import dev.fidil.fettle.command.BranchProtectionCommand
import dev.fidil.fettle.command.DependabotCommand
import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli
import org.reflections.Reflections
import java.util.*
import kotlin.system.exitProcess

object CommandFactory {
    inline fun <reified T> getImplementations(): List<Class<out T>> {
        val classes = mutableListOf<Class<out T>>()
        val reflections = Reflections("dev.fidil.fettle")
        val subTypes = reflections.getSubTypesOf(T::class.java)
        for (subType in subTypes) {
            classes.add(subType as Class<out T>)
        }
        return classes
    }
}
fun main(args: Array<String>) {
    val ghToken = System.getenv("GH_TOKEN")
    val ghUser = System.getenv("GH_USER")
    if (ghToken == null || ghUser == null) {
        println("A valid GitHub user and token are required to be in your environment. Example \nexport GH_TOKEN=sometoken\nexport GH_USER=myuser")
        exitProcess(1)
    }

    val parser = ArgParser("fettle-cli")
    parser.subcommands(BranchProtectionCommand(ghUser, ghToken), DependabotCommand(ghUser, ghToken))
    parser.parse(args)
    println("╭ᥥ╮(´• ᴗ •`˵)╭ᥥ╮")

}