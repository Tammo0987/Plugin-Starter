package com.github.plugin.api.step

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.plugin.model.Plugin
import com.github.plugin.model.pipeline.Step
import com.github.plugin.service.step.io.DirectoryCreationStep
import com.github.plugin.service.step.io.FileCreationStep
import com.hendraanggrian.javapoet.buildJavaFile
import com.hendraanggrian.javapoet.classOf
import java.util.*
import javax.lang.model.element.Modifier
import kotlin.io.path.ExperimentalPathApi

class SpigotStep : Step() {

    private val mapper =
        ObjectMapper(YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)).registerModule(KotlinModule())

    @ExperimentalPathApi
    override fun process(plugin: Plugin) {
        val metadata = plugin.metadata
        FileCreationStep(
            "${plugin.metadata.name}/src/main/resources/plugin.yaml",
            this.mapper.writeValueAsBytes(
                PluginYamlMetadata(
                    "${this.composePackage(plugin)}.${metadata.name.capitalize(Locale.ENGLISH)}",
                    metadata.name,
                    metadata.version,
                    metadata.description ?: "",
                    listOf(metadata.author ?: "")
                )
            )
        ).process(plugin)

        DirectoryCreationStep(
            "${plugin.metadata.name}/src/main/java/${this.composePackageAsPath(plugin)}"
        ).process(
            plugin
        )

        FileCreationStep(
            "${plugin.metadata.name}/src/main/java/${this.composePackageAsPath(plugin)}/${
                metadata.name.capitalize(
                    Locale.ENGLISH
                )
            }.java", this.composePluginClass(plugin).toByteArray()
        ).process(plugin)
    }

    private fun composePluginClass(plugin: Plugin): String {
        return buildJavaFile(this.composePackage(plugin)) {
            addClass(plugin.metadata.name.capitalize(Locale.ENGLISH)) {
                superclass = "org.bukkit.plugin.java".classOf("JavaPlugin")
                addModifiers(Modifier.PUBLIC)
            }
        }.toString()
    }

    private fun composePackage(plugin: Plugin): String =
        "${plugin.metadata.group}.${plugin.metadata.name}"

    private fun composePackageAsPath(plugin: Plugin): String = this.composePackage(plugin).replace(".", "/")

    private data class PluginYamlMetadata(
        val main: String,
        val name: String,
        val version: String,
        val description: String,
        val authors: List<String>
    )

}