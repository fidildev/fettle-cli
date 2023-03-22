@file:OptIn(ExperimentalCli::class)

package dev.fidil.fettle

import dev.fidil.fettle.CommandFactory.getImplementations
import dev.fidil.fettle.command.FettleCommand
import dev.fidil.fettle.command.FettleContext
import dev.fidil.fettle.config.GitHubConfig
import dev.fidil.fettle.handler.FettleHandler
import dev.fidil.fettle.handler.GitHubFettleHandler
import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli
import org.reflections.Reflections
import org.yaml.snakeyaml.Yaml
import java.lang.reflect.Modifier
import java.nio.file.Files
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

fun findFettleFunctions(user: String, token: String): FettleContext {
    val context = FettleContext(user, token)
    val fettleHandler = GitHubFettleHandler(context)
    for (implementation in getImplementations<FettleCommand>()) {
        context.commandMap[implementation.simpleName] =
            implementation.getDeclaredConstructor(FettleHandler::class.java).newInstance(fettleHandler)
    }

    return context
}

fun main(args: Array<String>) {
    createFettleConfigDirIfNotExists()
    checkFettleConfigPermissions()
    val (ghUser, ghToken) = getGitHubConfig()

    val parser = ArgParser("fettle-cli")
    val context = findFettleFunctions(ghUser, ghToken)
    parser.subcommands(*context.commandMap.values.toTypedArray())
    parser.parse(args)
    println("╭ᥥ╮(´• ᴗ •`˵)╭ᥥ╮")
}

fun createFettleConfigDirIfNotExists() {
    if (!GitHubConfig.fettleDir.exists()) {
        if (!GitHubConfig.fettleDir.mkdir()) {
            println("Warning: Failed to create the fettle config directory.")
        }
    }
}

fun checkFettleConfigPermissions() {
    if (!GitHubConfig.configFile.exists()) {
        return
    }
    val actualPermission = Files.getPosixFilePermissions(GitHubConfig.configFile.toPath())
    if (actualPermission != GitHubConfig.requiredFilePermission) {
        println("Fettle config has the wrong permission. Please set it to 600 for security reasons.")
        exitProcess(1)
    }
}

fun getGitHubConfig(): Pair<String, String> {
    var ghUser: String? = null
    var ghToken: String? = null

    if (GitHubConfig.configFile.exists()) {
        val yaml = Yaml()
        val configData = yaml.loadAs(GitHubConfig.configFile.inputStream(), GitHubConfig::class.java)

        if (configData != null) {
            val githubConfig = configData.github
            ghUser = githubConfig.user
            ghToken = githubConfig.token
        }
    }

    if (ghUser == null) {
        ghUser = System.getenv("GH_USER")
    }

    if (ghToken == null) {
        ghToken = System.getenv("GH_TOKEN")
    }

    if (ghToken == null || ghUser == null) {
        println("A valid GitHub user and token are required to be in your fettle config or in your environment. Environment Example: \nexport GH_TOKEN=sometoken\nexport GH_USER=myuser")
        exitProcess(1)
    }

    return Pair(ghUser, ghToken)
}
