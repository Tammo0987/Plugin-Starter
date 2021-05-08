package com.github.plugin.generator.service.step.build.maven

import com.github.plugin.generator.model.Plugin
import com.github.plugin.generator.model.dependency.Dependency
import com.github.plugin.generator.model.language.Language
import com.github.plugin.generator.model.pipeline.Step
import com.github.plugin.generator.model.repository.Repository
import com.github.plugin.generator.service.step.io.FileCreationStep
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.XmlVersion
import org.redundent.kotlin.xml.xml
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi

class MavenPomGenerateStep : Step() {

    @ExperimentalPathApi
    override fun process(plugin: Plugin, workingDirectory: Path) {
        FileCreationStep(
            "${plugin.metadata.name}/pom.xml",
            this.generatePom(plugin).toByteArray(Charsets.UTF_8)
        ).process(plugin, workingDirectory)
    }

    private fun generatePom(plugin: Plugin): String {
        if (plugin.language == Language.KOTLIN) {
            plugin.repositories.add(Repository("mavenCentral", "https://repo1.maven.org/maven2/"))

            plugin.dependencies.add(
                Dependency(
                    "org.jetbrains.kotlin",
                    "kotlin-stdlib-jdk8",
                    "1.5.0"
                )
            )
        }

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

            if (plugin.language == Language.KOTLIN) {
                emptyLine()
                createKotlinBuildProcess()
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

    private fun Node.createKotlinBuildProcess() {
        val build = xml("build") {
            element("sourceDirectory", "src/main/kotlin")
            element("testSourceDirectory", "src/test/kotlin")

            "plugins" {
                "plugin" {
                    element("groupId", "org.jetbrains.kotlin")
                    element("artifactId", "kotlin-maven-plugin")
                    element("version", "1.5.0")

                    "executions" {
                        "execution" {
                            element("id", "compile")
                            element("phase", "compile")
                            "goals" {
                                element("goal", "compile")
                            }
                        }
                        "execution" {
                            element("id", "test-compile")
                            element("phase", "test-compile")
                            "goals" {
                                element("goal", "test-compile")
                            }
                        }
                    }
                }
            }
        }

        addNode(build)
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