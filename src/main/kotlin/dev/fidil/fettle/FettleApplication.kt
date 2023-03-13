@file:OptIn(ExperimentalCli::class)

package dev.fidil.fettle

import dev.fidil.fettle.CommandFactory.getImplementations
import dev.fidil.fettle.command.CommandContext
import dev.fidil.fettle.command.FettleCommand
import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli
import org.reflections.Reflections
import java.lang.reflect.Modifier
import java.util.*
import kotlin.system.exitProcess

object CommandFactory {
    inline fun <reified T> getImplementations(): List<Class<out T>> {
        val classes = mutableListOf<Class<out T>>()
        val reflections = Reflections("dev.fidil.fettle")
        val subTypes = reflections.getSubTypesOf(T::class.java)
        for (subType in subTypes) {
            if (Modifier.isAbstract(subType.modifiers)) {
                continue
            }
            classes.add(subType as Class<out T>)
        }
        return classes
    }
}

fun createCommandContext(user: String, token: String): CommandContext {
    val context = CommandContext(user, token)
    for (implementation in getImplementations<FettleCommand>()) {
        context.commandMap[implementation.simpleName] =
            implementation.getDeclaredConstructor(CommandContext::class.java).newInstance(context)
    }
    return context
}

fun main(args: Array<String>) {
    val ghToken = System.getenv("GH_TOKEN")
    val ghUser = System.getenv("GH_USER")
    if (ghToken == null || ghUser == null) {
        println("A valid GitHub user and token are required to be in your environment. Example \nexport GH_TOKEN=sometoken\nexport GH_USER=myuser")
        exitProcess(1)
    }

    val parser = ArgParser("fettle-cli")
    val context = createCommandContext(ghUser, ghToken)
    parser.subcommands(*context.commandMap.values.toTypedArray())
    parser.parse(args)
    println("╭ᥥ╮(´• ᴗ •`˵)╭ᥥ╮")

}