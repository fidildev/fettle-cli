@file:OptIn(ExperimentalCli::class)

package dev.fidil.fettle

import dev.fidil.fettle.command.BranchProtectionCommand
import dev.fidil.fettle.command.DependabotCommand
import dev.fidil.fettle.config.GitHubConfig
import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.PosixFilePermissions
import java.util.*
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    createFettleConfigIfNotExists()
    val (ghUser, ghToken) = getGitHubConfig()

    val parser = ArgParser("fettle-cli")
    parser.subcommands(BranchProtectionCommand(ghUser, ghToken), DependabotCommand(ghUser, ghToken))
    parser.parse(args)

    println("╭ᥥ╮(´• ᴗ •`˵)╭ᥥ╮")
}
fun createFettleConfigIfNotExists() {
    val fettleDir = File(System.getProperty("user.home"), ".fettle")
    val configFile = File(fettleDir, "config.yaml")
    val requiredFilePermission = PosixFilePermissions.fromString("rw-------")

    if (!fettleDir.exists()) {
        if (fettleDir.mkdir()) {
        } else {
            println("Failed to create the fettle config directory.")
            exitProcess(1)
        }
    }
    if (!configFile.exists()) {
        try {
            configFile.createNewFile()
            Files.setPosixFilePermissions(
                configFile.toPath(),
                requiredFilePermission
            )
        } catch (ex: Exception) {
            println("Failed to create the fettle config file")
            exitProcess(1)
        }
    }
    else {
        val actualPermission = Files.getPosixFilePermissions(configFile.toPath())
        if (actualPermission != requiredFilePermission) {
            println("Fettle config has the wrong permission. Please set it to 600")
            exitProcess(1)
        }
    }
}

fun getGitHubConfig(): Pair<String, String> {
    val fettleDir = File(System.getProperty("user.home"), ".fettle")
    val configFile = File(fettleDir, "config.yaml")

    val yaml = Yaml()
    val configData = yaml.loadAs(configFile.inputStream(), GitHubConfig::class.java)

    var ghUser: String? = null
    var ghToken: String? = null

    if (configData != null) {
        val githubConfig = configData.github
        ghUser = githubConfig.user
        ghToken = githubConfig.token
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