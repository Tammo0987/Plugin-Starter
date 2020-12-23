package com.github.plugin.generator.service

import com.github.plugin.generator.factory.BuildToolGenerateStepFactory
import com.github.plugin.generator.model.Plugin
import com.github.plugin.generator.model.pipeline.Pipeline
import com.github.plugin.generator.service.step.io.DirectoryCreationStep
import java.nio.file.Path

object PluginGenerateService {

    // TODO add working path for generating
    fun generate(plugin: Plugin, path: Path) =
        this.mapPluginToPipeline(plugin).execute(plugin)

    private fun mapPluginToPipeline(plugin: Plugin): Pipeline {
        val pipeline = Pipeline()

        val name = plugin.metadata.name

        val steps = listOf(
            DirectoryCreationStep(name),
            BuildToolGenerateStepFactory.createBuildStructureStep(plugin.buildTool),
            plugin.api.step,
            BuildToolGenerateStepFactory.createBuildSettingsStep(plugin.buildTool)
        )

        pipeline.appendSteps(steps)

        return pipeline
    }

}