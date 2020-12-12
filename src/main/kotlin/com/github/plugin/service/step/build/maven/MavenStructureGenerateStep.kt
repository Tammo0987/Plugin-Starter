package com.github.plugin.service.step.build.maven

import com.github.plugin.model.Plugin
import com.github.plugin.model.pipeline.Pipeline
import com.github.plugin.model.pipeline.Step
import com.github.plugin.service.step.io.DirectoryCreationStep

class MavenStructureGenerateStep : Step() {

    override fun process(plugin: Plugin) {
        val pipeline = Pipeline()
        val name = plugin.metadata.name

        val directories = listOf("main/java", "main/resources", "test/java", "test/resources")
        val steps = directories.map { "${name}/src/${it}" }.map { DirectoryCreationStep(it) }

        pipeline.appendSteps(steps)

        pipeline.execute(plugin)
    }

}