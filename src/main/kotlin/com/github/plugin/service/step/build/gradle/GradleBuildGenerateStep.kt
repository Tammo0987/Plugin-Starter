package com.github.plugin.service.step.build.gradle

import com.github.plugin.model.Plugin
import com.github.plugin.model.pipeline.Step
import com.github.plugin.service.step.io.FileCreationStep
import com.squareup.kotlinpoet.CodeBlock
import kotlin.io.path.ExperimentalPathApi

class GradleBuildGenerateStep : Step() {

    @ExperimentalPathApi
    override fun process(plugin: Plugin) {
        FileCreationStep(
            "${plugin.metadata.name}/build.gradle.kts",
            this.generateBuildFileContent(plugin).toByteArray()
        ).process(plugin)

        FileCreationStep(
            "${plugin.metadata.name}/settings.gradle.kts",
            this.generateSettingsFileContent(plugin).toByteArray()
        ).process(plugin)
    }

    private fun generateBuildFileContent(plugin: Plugin): String {
        val kotlinCompileImport = "import org.jetbrains.kotlin.gradle.tasks.KotlinCompile"

        val kotlinVersion = "1.4.21"
        val kotlinPlugin = "kotlin(\"jvm\") version \"${kotlinVersion}\""

        val pluginBlock = CodeBlock.builder()
            .beginControlFlow("plugins")
            .addStatement(kotlinPlugin)
            .endControlFlow()
            .build()
            .toString()

        val group = "group = \"${plugin.metadata.group}\"\n"
        val version = "version = \"${plugin.metadata.version}\"\n"

        val mavenCentral = "mavenCentral()"

        val repositoryBlock = CodeBlock.builder()
            .beginControlFlow("repositories")
            .addStatement(mavenCentral)
            .endControlFlow()
            .build()
            .toString()

        val kotlinTarget = "kotlinOptions.jvmTarget = \"1.8\""

        val kotlinCompileTask = CodeBlock.builder()
            .beginControlFlow("tasks.withType<KotlinCompile>")
            .addStatement(kotlinTarget)
            .endControlFlow()
            .build()
            .toString()

        return kotlinCompileImport +
                lineBreak() +
                lineBreak() +
                pluginBlock +
                lineBreak() +
                group +
                version +
                lineBreak() +
                repositoryBlock +
                lineBreak() +
                kotlinCompileTask
    }

    private fun generateSettingsFileContent(plugin: Plugin): String {
        return "rootProject.name = \"${plugin.metadata.name}\"\n"
    }

    private fun lineBreak(): String = "\n"

}