package dev.fidil.fettle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FettleApplication

fun main(args: Array<String>) {
    runApplication<FettleApplication>(*args)
}
