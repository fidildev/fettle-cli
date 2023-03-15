@file:OptIn(ExperimentalCli::class)

package dev.fidil.fettle

import dev.fidil.fettle.command.BranchProtectionCommand
import dev.fidil.fettle.command.DependabotCommand
import dev.fidil.fettle.command.StaticCodeAnalysisCommand
import dev.fidil.fettle.config.GitHubConfig
import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli
import org.yaml.snakeyaml.Yaml
import java.nio.file.Files
import java.util.*
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    createFettleConfigDirIfNotExists()
    checkFettleConfigPermissions()
    val (ghUser, ghToken) = getGitHubConfig()

    val parser = ArgParser("fettle-cli")
    parser.subcommands(BranchProtectionCommand(ghUser, ghToken), DependabotCommand(ghUser, ghToken), StaticCodeAnalysisCommand(ghUser, ghToken))
    parser.parse(args)

    println("╭ᥥ╮(´• ᴗ •`˵)╭ᥥ╮")
}
fun createFettleConfigDirIfNotExists() {
    if (!GitHubConfig.fettleDir.exists()) {
        if (GitHubConfig.fettleDir.mkdir()) {
        } else {
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