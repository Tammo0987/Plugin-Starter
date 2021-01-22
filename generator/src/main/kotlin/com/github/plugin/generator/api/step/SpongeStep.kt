package com.github.plugin.generator.api.step

import com.github.plugin.generator.model.Plugin
import com.github.plugin.generator.model.language.Language
import com.github.plugin.generator.model.pipeline.Step
import com.github.plugin.generator.service.step.io.DirectoryCreationStep
import com.github.plugin.generator.service.step.io.FileCreationStep
import com.hendraanggrian.javapoet.buildAnnotationSpec
import com.hendraanggrian.javapoet.buildJavaFile
import com.squareup.javapoet.ClassName
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import java.nio.file.Path
import java.util.*
import javax.lang.model.element.Modifier
import kotlin.io.path.ExperimentalPathApi

class SpongeStep : Step() {

    @ExperimentalPathApi
    override fun process(plugin: Plugin, workingDirectory: Path) {
        val metadata = plugin.metadata

        val path = "${metadata.name}/src/main/${plugin.language.toString().toLowerCase()}/${
            this.composePackageAsPath(plugin)
        }"

        DirectoryCreationStep(path).process(
            plugin, workingDirectory
        )

        val fileSuffix = when (plugin.language) {
            Language.JAVA -> "java"
            Language.KOTLIN -> "kt"
        }

        FileCreationStep(
            "$path/${metadata.name.capitalize(Locale.ENGLISH)}.${fileSuffix}",
            this.composePluginClass(plugin).toByteArray()
        ).process(plugin, workingDirectory)
    }

    private fun composePluginClass(plugin: Plugin): String {
        return when (plugin.language) {
            Language.JAVA -> this.createJavaPluginClass(plugin)
            Language.KOTLIN -> this.createKotlinPluginClass(plugin)
        }
    }

    private fun createJavaPluginClass(plugin: Plugin): String {
        return buildJavaFile(this.composePackage(plugin)) {
            addClass(plugin.metadata.name.capitalize(Locale.ENGLISH)) {
                annotations {
                    val className = ClassName.get("org.spongepowered.api.plugin", "Plugin")

                    val annotation = buildAnnotationSpec(className) {
                        addMember("id", "\"${plugin.metadata.name.toLowerCase()}\"")
                        addMember("name", "\"${plugin.metadata.name}\"")
                        addMember("version", "\"${plugin.metadata.version}\"")
                        plugin.metadata.description?.let { addMember("description", "\"$it\"") }
                    }

                    add(annotation)
                }
                addModifiers(Modifier.PUBLIC)
            }
        }.toString()
    }

    private fun createKotlinPluginClass(plugin: Plugin): String {
        val name = plugin.metadata.name.capitalize(Locale.ENGLISH)

        val annotation =
            AnnotationSpec.builder(com.squareup.kotlinpoet.ClassName("org.spongepowered.api.plugin", "Plugin"))
                .addMember("id = \"${plugin.metadata.name.toLowerCase()}\"")
                .addMember("name = \"${plugin.metadata.name}\"")
                .addMember("version = \"${plugin.metadata.version}\"")
                .addMember("description = \"${plugin.metadata.description}\"")
                .build()


        return FileSpec.builder(this.composePackage(plugin), name)
            .addType(
                TypeSpec.classBuilder(name)
                    .addAnnotation(annotation)
                    .build()
            )
            .build()
            .toString()
            .replace("public class", "class")
    }

    private fun composePackage(plugin: Plugin): String =
        "${plugin.metadata.group}.${plugin.metadata.name}"

    private fun composePackageAsPath(plugin: Plugin): String = this.composePackage(plugin).replace(".", "/")
}