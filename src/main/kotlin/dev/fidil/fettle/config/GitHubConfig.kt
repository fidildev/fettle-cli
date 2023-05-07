package dev.fidil.fettle.config

import java.io.File
import java.nio.file.attribute.PosixFilePermissions

data class GitHubConfig(var github: GitHub) {
    companion object {
        val fettleDir = File(System.getProperty("user.home"), ".fettle")
        val configFile = File(fettleDir, "config.yaml")
        val requiredFilePermission = PosixFilePermissions.fromString("rw-------")
    }

    constructor() : this(GitHub("", ""))
}

data class GitHub(var user: String?, var token: String?) {
    constructor() : this(null, null)
}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class FettleFunction(val name: String)
