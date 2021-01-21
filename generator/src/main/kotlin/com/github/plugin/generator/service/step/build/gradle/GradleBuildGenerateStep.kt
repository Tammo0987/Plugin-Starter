package com.github.plugin.generator.service.step.build.gradle

import com.github.plugin.generator.model.Plugin
import com.github.plugin.generator.model.dependency.Dependency
import com.github.plugin.generator.model.dependency.DependencyScope
import com.github.plugin.generator.model.language.Language
import com.github.plugin.generator.model.pipeline.Step
import com.github.plugin.generator.model.repository.Repository
import com.github.plugin.generator.service.step.io.FileCreationStep
import com.squareup.kotlinpoet.CodeBlock
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi

class GradleBuildGenerateStep : Step() {

    @ExperimentalPathApi
    override fun process(plugin: Plugin, workingDirectory: Path) {
        FileCreationStep(
            "${plugin.metadata.name}/build.gradle.kts",
            this.generateBuildFileContent(plugin).toByteArray()
        ).process(plugin, workingDirectory)

        FileCreationStep(
            "${plugin.metadata.name}/settings.gradle.kts",
            this.generateSettingsFileContent(plugin).toByteArray()
        ).process(plugin, workingDirectory)
    }

    private fun generateBuildFileContent(plugin: Plugin): String {
        val pluginBlock = CodeBlock.builder()
            .beginControlFlow("plugins")
            .addStatement(this.createLanguagePluginStatement(plugin))
            .endControlFlow()
            .build()
            .toString()

        val group = "group = \"${plugin.metadata.group}\"\n"
        val version = "version = \"${plugin.metadata.version}\"\n"

        val mavenCentral = "mavenCentral()"

        val repositories = CodeBlock.builder()
            .beginControlFlow("repositories")
            .addStatement(mavenCentral)

        generateRepositories(plugin.repositories).forEach { repositories.add(it) }

        val repositoryBlock = repositories
            .endControlFlow()
            .build()
            .toString()

        return pluginBlock +
                lineBreak() +
                group +
                version +
                lineBreak() +
                repositoryBlock +
                lineBreak() +
                generateDependencies(plugin)
    }

    private fun createLanguagePluginStatement(plugin: Plugin): String {
        return when (plugin.language) {
            Language.JAVA -> "java"
            Language.KOTLIN -> "kotlin(\"jvm\") version \"1.4.21\""
        }
    }

    private fun generateRepositories(repositories: List<Repository>): List<CodeBlock> {
        return repositories.map { mapRepositoryToCodeBlock(it) }
    }

    private fun mapRepositoryToCodeBlock(repository: Repository): CodeBlock {
        return CodeBlock.builder()
            .beginControlFlow("maven")
            .addStatement("name = \"${repository.id}\"")
            .addStatement("url = uri(\"${repository.url}\")")
            .endControlFlow()
            .build()
    }

    private fun generateDependencies(plugin: Plugin): String {
        val dependencyCodeBlock = CodeBlock.builder()

        if (plugin.language == Language.KOTLIN) {
            dependencyCodeBlock.addStatement("implementation(kotlin(\"stdlib\"))")
        }

        plugin.dependencies.map { mapDependency(it) }.forEach { dependencyCodeBlock.addStatement(it) }

        val dependencyBlock = dependencyCodeBlock.build()

        return CodeBlock.builder()
            .beginControlFlow("dependencies")
            .add(dependencyBlock)
            .endControlFlow()
            .build()
            .toString()
    }

    private fun mapDependency(dependency: Dependency): String {
        val scope = when (dependency.scope) {
            DependencyScope.COMPILE -> "implementation"
            DependencyScope.PROVIDED -> "compileOnly"
            DependencyScope.TEST -> "testCompile"
        }
        return "${scope}(\"${dependency.group}:${dependency.artifact}:${dependency.version}\")"
    }

    private fun generateSettingsFileContent(plugin: Plugin): String {
        return "rootProject.name = \"${plugin.metadata.name}\"\n"
    }

    private fun lineBreak(): String = "\n"

}