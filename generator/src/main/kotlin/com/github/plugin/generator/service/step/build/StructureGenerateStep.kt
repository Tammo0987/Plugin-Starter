package com.github.plugin.generator.service.step.build

import com.github.plugin.generator.model.Plugin
import com.github.plugin.generator.model.pipeline.Pipeline
import com.github.plugin.generator.model.pipeline.Step
import com.github.plugin.generator.service.step.io.DirectoryCreationStep
import java.nio.file.Path

class StructureGenerateStep : Step() {

    override fun process(plugin: Plugin, workingDirectory: Path) {
        val pipeline = Pipeline()
        val name = plugin.metadata.name

        val language = plugin.language.toString().toLowerCase()

        val directories = listOf("main/$language", "main/resources", "test/$language", "test/resources")
        val steps = directories.map { "${name}/src/${it}" }.map { DirectoryCreationStep(it) }

        pipeline.appendSteps(steps)

        pipeline.execute(plugin, workingDirectory)
    }

}