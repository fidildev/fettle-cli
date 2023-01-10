package dev.fidil.fettle.endpoint

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RestEndpoint(val client: GitHubClient) {

    @GetMapping("/{org}/{repo}/{branch}")
    fun check(@PathVariable("org") org: String,
            @PathVariable("repo") repo: String,
                  @PathVariable("branch") branch: String): Boolean {
        return client.branchProtectionEnabled(org, repo, branch)
    }
}