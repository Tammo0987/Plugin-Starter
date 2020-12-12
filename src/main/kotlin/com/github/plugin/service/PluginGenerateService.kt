package com.github.plugin.service

import com.github.plugin.factory.BuildToolGenerateStepFactory
import com.github.plugin.model.Plugin
import com.github.plugin.model.pipeline.Pipeline
import com.github.plugin.service.step.io.DirectoryCreationStep

class PluginGenerateService {

    fun generate(plugin: Plugin) =
        this.mapPluginToPipeline(plugin).execute(plugin)

    private fun mapPluginToPipeline(plugin: Plugin): Pipeline {
        val pipeline = Pipeline()

        val name = plugin.metadata.name

        val steps = listOf(
            DirectoryCreationStep(name),
            BuildToolGenerateStepFactory.createBuildSettingsStep(plugin.buildTool),
            BuildToolGenerateStepFactory.createBuildStructureStep(plugin.buildTool)
        )

        pipeline.appendSteps(steps)

        return pipeline
    }

}