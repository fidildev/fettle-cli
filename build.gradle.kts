plugins {
    kotlin("jvm") version "1.8.10"
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
    application
}

description = "Fettle Cli"
group = "dev.fidil"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.kohsuke:github-api:1.314")
    implementation("org.jetbrains.kotlinx", "kotlinx-cli", "0.3.5")
    implementation("org.yaml:snakeyaml:2.0")
    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}

application {
    mainClass.set("dev.fidil.fettle.FettleApplicationKt")
}

kotlin {
    jvmToolchain(17)
}

tasks {
    test {
        useJUnitPlatform()
    }

    jar {
        manifest {
            attributes["Implementation-Title"] = project.description
            attributes["Implementation-Version"] = project.version
        }
    }

    withType<Tar> {
        archiveExtension.set("tar.gz")
        compression = Compression.GZIP
    }

    register("installGitHook", Copy::class) {
        from("scripts/pre-commit")
        into(".git/hooks")
        fileMode
    }

    named("build") { dependsOn("addKtlintFormatGitPreCommitHook") }
}
