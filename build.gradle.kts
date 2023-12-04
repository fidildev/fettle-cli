plugins {
    kotlin("jvm") version "1.8.21"
    id("org.jlleitschuh.gradle.ktlint") version "12.0.2"
    application
}

description = "Fettle Cli"
group = "dev.fidil"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.kohsuke:github-api:1.317")
    implementation("org.jetbrains.kotlinx", "kotlinx-cli", "0.3.5")
    implementation("org.yaml:snakeyaml:2.2")
    // https://mvnrepository.com/artifact/org.reflections/reflections
    implementation("org.reflections:reflections:0.10.2") {
        exclude("org.slf4j")
    }
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation("org.slf4j:slf4j-simple:2.0.9")

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

//    named("build") { dependsOn("addKtlintFormatGitPreCommitHook") }
//    named("addKtlintFormatGitPreCommitHook") { dependsOn("compileKotlin") }
}
