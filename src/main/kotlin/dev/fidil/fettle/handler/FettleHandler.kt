package dev.fidil.fettle.handler

import dev.fidil.fettle.command.CommandResult
import dev.fidil.fettle.command.FettleContext
import dev.fidil.fettle.config.FettleFunction

interface FettleHandler {

    val context: FettleContext

    @FettleFunction(
        "Branch Protection",
        "Turn on branch protection for your default branch as a best practice."
    )
    fun branchProtections(org: String, repo: String, branch: String): CommandResult

    @FettleFunction(
        "Dependabot",
        "Enable Dependabot on your repo to check for security issues."
    )
    fun dependabot(org: String, repo: String, branch: String): CommandResult

    @FettleFunction(
        "Deployment Pipelines",
        "Add GitHub Action Workflows to your repo to build and deploy the software."
    )
    fun deploymentPipelines(org: String, repo: String, branch: String): CommandResult

    @FettleFunction(
        "Static Analysis",
        "Add Codacy integration to your repo for Static Code Analysis."
    )
    fun staticAnalysis(org: String, repo: String, branch: String): CommandResult

    @FettleFunction(
        "Code Coverage",
        "Add jacoco to your repo to measure code coverage."
    )
    fun codeCoverage(org: String, repo: String, branch: String): CommandResult

    @FettleFunction(
        "Readme",
        "Add a Readme to your repo to inform others about what this repo is about."
    )
    fun readme(org: String, repo: String, branch: String): CommandResult

    fun score(org: String, repo: String): CommandResult

    fun orgScore(org: String): CommandResult
}
