package com.github.plugin.factory

import com.github.plugin.model.Plugin
import com.github.plugin.model.build.BuildTool
import com.github.plugin.model.pipeline.Step
import com.github.plugin.service.step.build.MavenPomGenerateStep

object BuildToolGenerateStepFactory {

    fun create(buildTool: BuildTool): Step {
        return when (buildTool) {
            BuildTool.MAVEN -> MavenPomGenerateStep()
            else -> object : Step() {
                override fun process(plugin: Plugin) {
                    TODO("Not yet implemented")
                }
            }
        }
    }

}