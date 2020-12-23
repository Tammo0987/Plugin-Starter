package com.github.plugin.generator.service.step.build

import com.github.plugin.generator.model.Plugin
import com.github.plugin.generator.model.pipeline.Pipeline
import com.github.plugin.generator.model.pipeline.Step
import com.github.plugin.generator.service.step.io.DirectoryCreationStep

class StructureGenerateStep : Step() {

    override fun process(plugin: Plugin) {
        val pipeline = Pipeline()
        val name = plugin.metadata.name

        val directories = listOf("main/java", "main/resources", "test/java", "test/resources")
        val steps = directories.map { "${name}/src/${it}" }.map { DirectoryCreationStep(it) }

        pipeline.appendSteps(steps)

        pipeline.execute(plugin)
    }

}