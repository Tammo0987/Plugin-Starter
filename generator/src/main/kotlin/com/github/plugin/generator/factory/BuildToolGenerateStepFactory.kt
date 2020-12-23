package com.github.plugin.generator.factory

import com.github.plugin.generator.model.build.BuildTool
import com.github.plugin.generator.model.pipeline.Step
import com.github.plugin.generator.service.step.build.StructureGenerateStep
import com.github.plugin.generator.service.step.build.gradle.GradleBuildGenerateStep
import com.github.plugin.generator.service.step.build.maven.MavenPomGenerateStep

object BuildToolGenerateStepFactory {

    fun createBuildSettingsStep(buildTool: BuildTool): Step {
        return when (buildTool) {
            BuildTool.MAVEN -> MavenPomGenerateStep()
            BuildTool.GRADLE -> GradleBuildGenerateStep()
        }
    }

    fun createBuildStructureStep(buildTool: BuildTool): Step {
        return when (buildTool) {
            BuildTool.MAVEN, BuildTool.GRADLE -> StructureGenerateStep()
        }
    }

}