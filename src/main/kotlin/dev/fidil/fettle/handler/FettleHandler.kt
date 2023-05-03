package dev.fidil.fettle.handler

import dev.fidil.fettle.command.CommandResult
import dev.fidil.fettle.command.FettleContext

interface FettleHandler {

    val context: FettleContext

    fun branchProtections(org: String, repo: String, branch: String): CommandResult

    fun dependabot(org: String, repo: String, branch: String): CommandResult

    fun deploymentPipelines(org: String, repo: String, branch: String): CommandResult

    fun staticAnalysis(org: String, repo: String, branch: String): CommandResult

    fun codeCoverage(org: String, repo: String, branch: String): CommandResult

    fun score(org: String, repo: String, branch: String): CommandResult

    fun readme(org: String, repo: String, branch: String): CommandResult
}
