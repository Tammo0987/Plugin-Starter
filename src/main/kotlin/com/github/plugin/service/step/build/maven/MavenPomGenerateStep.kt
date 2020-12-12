package com.github.plugin.service.step.build.maven

import com.github.plugin.model.Plugin
import com.github.plugin.model.dependency.Dependency
import com.github.plugin.model.pipeline.Step
import com.github.plugin.model.repository.Repository
import com.github.plugin.service.step.io.FileCreationStep
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.XmlVersion
import org.redundent.kotlin.xml.xml
import kotlin.io.path.ExperimentalPathApi

class MavenPomGenerateStep : Step() {

    @ExperimentalPathApi
    override fun process(plugin: Plugin) {
        FileCreationStep(
            "${plugin.metadata.name}/pom.xml",
            this.generatePom(plugin).toByteArray(Charsets.UTF_8)
        ).process(plugin)
    }

    private fun generatePom(plugin: Plugin): String {
        return xml("project") {
            globalProcessingInstruction(
                "xml",
                Pair("version", XmlVersion.V10.value),
                Pair("encoding", Charsets.UTF_8.toString())
            )

            xmlns = "http://maven.apache.org/POM/4.0.0"

            element("modelVersion", "4.0.0")

            emptyLine()

            element("groupId", plugin.metadata.group)
            element("artifactId", plugin.metadata.name)
            element("version", plugin.metadata.version)

            emptyLine()

            if (plugin.repositories.isNotEmpty()) {
                createRepositories(plugin)
            }

            emptyLine()

            if (plugin.dependencies.isNotEmpty()) {
                createDependencies(plugin)
            }
        }.toString(PrintOptions(true, singleLineTextElements = true, useSelfClosingTags = false))
    }

    private fun Node.createRepositories(plugin: Plugin) {
        val repositories = xml("repositories") {
            plugin.repositories.forEach { createRepository(it) }
        }

        addNode(repositories)
    }

    private fun Node.createRepository(repository: Repository): Node {
        return element("repository") {
            element("id", repository.id)
            element("url", repository.url)
        }
    }

    private fun Node.createDependencies(plugin: Plugin) {
        val dependencies = xml("dependencies") {
            plugin.dependencies.forEach { createDependency(it) }
        }

        addNode(dependencies)
    }

    private fun Node.createDependency(dependency: Dependency): Node {
        return element("dependency") {
            element("groupId", dependency.group)
            element("artifactId", dependency.artifact)
            element("version", dependency.version)
            element("scope", dependency.scope.name.toLowerCase())
        }
    }

    private fun Node.emptyLine() {
        addNode(object : Node("lineBreak") {
            override fun render(builder: Appendable, indent: String, printOptions: PrintOptions) {
                builder.append("\n")
            }
        })
    }
}