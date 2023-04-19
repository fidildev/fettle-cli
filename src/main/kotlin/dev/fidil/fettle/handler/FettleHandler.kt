package dev.fidil.fettle.handler

import dev.fidil.fettle.command.CommandResult
import dev.fidil.fettle.command.FettleContext
import org.reflections.Reflections
import java.lang.reflect.Modifier

interface FettleHandler {

    val context: FettleContext

    object CommandFactory {
        inline fun <reified T> getImplementations(): List<Class<out T>> {
            val classes = mutableListOf<Class<out T>>()
            val reflections = Reflections("dev.fidil.fettle.handler")
            val subTypes = reflections.getSubTypesOf(T::class.java)
            for (subType in subTypes) {
                if (Modifier.isAbstract(subType.modifiers)) {
                    continue
                }
                classes.add(subType as Class<out T>)
            }
            return classes
        }
    }

    fun getScoreHandlers(): Map<String, FettleHandler> {
        val scoreMethodMap = HashMap<String, FettleHandler>()
        for (implementation in CommandFactory.getImplementations<FettleHandler>()) {
            if (implementation.simpleName == "score") {
                continue
            } else {
                scoreMethodMap[implementation.simpleName] =
                    implementation.getDeclaredConstructor(FettleContext::class.java).newInstance(context)
            }
        }

        return scoreMethodMap
    }

    fun branchProtections(org: String, repo: String, branch: String): CommandResult

    fun dependabot(org: String, repo: String, branch: String): CommandResult

    fun deploymentPipelines(org: String, repo: String, branch: String): CommandResult

    fun staticAnalysis(org: String, repo: String, branch: String): CommandResult

    fun codeCoverage(org: String, repo: String, branch: String): CommandResult

    fun score(org: String, repo: String, branch: String): CommandResult
}
