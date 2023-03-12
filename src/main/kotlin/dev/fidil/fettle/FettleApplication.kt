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
    checkFettleConfigPermissions()
    val (ghUser, ghToken) = getGitHubConfig()

    val parser = ArgParser("fettle-cli")
    parser.subcommands(BranchProtectionCommand(ghUser, ghToken), DependabotCommand(ghUser, ghToken))
    parser.parse(args)

    println("╭ᥥ╮(´• ᴗ •`˵)╭ᥥ╮")
}
fun createFettleConfigIfNotExists() {
    if (!GitHubConfig.fettleDir.exists()) {
        if (GitHubConfig.fettleDir.mkdir()) {
        } else {
            println("Failed to create the fettle config directory.")
            exitProcess(1)
        }
    }
    if (!GitHubConfig.configFile.exists()) {
        try {
            GitHubConfig.configFile.createNewFile()
            Files.setPosixFilePermissions(
                GitHubConfig.configFile.toPath(),
                GitHubConfig.requiredFilePermission
            )
        } catch (ex: Exception) {
            println("Failed to create the fettle config file")
            exitProcess(1)
        }
    }
}

fun checkFettleConfigPermissions() {
    val actualPermission = Files.getPosixFilePermissions(GitHubConfig.configFile.toPath())
    if (actualPermission != GitHubConfig.requiredFilePermission) {
        println("Fettle config has the wrong permission. Please set it to 600 for security reasons.")
        exitProcess(1)
    }
}

fun getGitHubConfig(): Pair<String, String> {
    val yaml = Yaml()
    val configData = yaml.loadAs(GitHubConfig.configFile.inputStream(), GitHubConfig::class.java)

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