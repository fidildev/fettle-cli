package dev.fidil.fettle.handler

import dev.fidil.fettle.command.CommandResult
import dev.fidil.fettle.command.FettleContext
import dev.fidil.fettle.config.FettleFunction

interface FettleHandler {

    val context: FettleContext

    @FettleFunction("Branch Protection")
    fun branchProtections(org: String, repo: String, branch: String): CommandResult

    @FettleFunction("Dependabot")
    fun dependabot(org: String, repo: String, branch: String): CommandResult

    @FettleFunction("Deployment Pipelines")
    fun deploymentPipelines(org: String, repo: String, branch: String): CommandResult

    @FettleFunction("Static Analysis")
    fun staticAnalysis(org: String, repo: String, branch: String): CommandResult

    @FettleFunction("Code Coverage")
    fun codeCoverage(org: String, repo: String, branch: String): CommandResult

    @FettleFunction("Readme")
    fun readme(org: String, repo: String, branch: String): CommandResult

    fun score(org: String, repo: String, branch: String): CommandResult
}
