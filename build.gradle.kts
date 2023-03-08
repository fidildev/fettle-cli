plugins {
    kotlin("jvm") version "1.8.10"
    application
}

description = "Fettle Cli"
group = "dev.fidil"
version = "0.0.1-SNAPSHOT"
//java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.kohsuke:github-api:1.313")
    implementation("org.jetbrains.kotlinx", "kotlinx-cli", "0.3.5")
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
}


//tasks.withType<KotlinCompile> {
//    kotlinOptions {
//        freeCompilerArgs = listOf("-Xjsr305=strict")
//        jvmTarget = "17"
//    }
//}
//
//tasks.withType<Test> {
//    useJUnitPlatform()
//}
