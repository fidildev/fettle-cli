package dev.fidil.fettle.config

data class GitHubConfig(var github: GitHub) {
    constructor() : this(GitHub("", ""))
}

data class GitHub(var user: String?, var token: String?) {
    constructor() : this(null, null)
}
