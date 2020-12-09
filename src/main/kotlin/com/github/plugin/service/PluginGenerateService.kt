package com.github.plugin.service

import com.github.plugin.model.Plugin
import com.github.plugin.model.PluginMetadata
import com.github.plugin.model.build.BuildTool
import com.github.plugin.model.pipeline.Pipeline
import com.github.plugin.service.step.build.MavenPomGenerateStep
import com.github.plugin.service.step.io.DirectoryCreationStep

class PluginGenerateService {

    fun generate(plugin: Plugin) =
        this.mapPluginToPipeline(plugin).execute(plugin)

    private fun mapPluginToPipeline(plugin: Plugin): Pipeline {
        val pipeline = Pipeline()

        pipeline.appendStep(DirectoryCreationStep(plugin.metadata.name))
        pipeline.appendStep(MavenPomGenerateStep())

        return pipeline
    }

}