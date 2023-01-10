package dev.fidil.fettle.endpoint

import org.kohsuke.github.GitHubBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service


@Service
class GitHubClient {

    @Value("\${github.token}")
    private lateinit var accessToken: String

    @Value("\${github.user}")
    private lateinit var githubUser: String

    fun branchProtectionEnabled(org: String, repo: String, branch: String): Boolean {
        val github = GitHubBuilder().withOAuthToken(accessToken, githubUser).build()
        return github.getRepository("$org/$repo").getBranch(branch).isProtected
    }

}