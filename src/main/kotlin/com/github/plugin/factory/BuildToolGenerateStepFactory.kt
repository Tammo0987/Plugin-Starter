package com.github.plugin.factory

import com.github.plugin.model.build.BuildTool
import com.github.plugin.model.pipeline.Step
import com.github.plugin.service.step.build.gradle.GradleBuildGenerateStep
import com.github.plugin.service.step.build.gradle.GradleStructureGenerateStep
import com.github.plugin.service.step.build.maven.MavenPomGenerateStep
import com.github.plugin.service.step.build.maven.MavenStructureGenerateStep

object BuildToolGenerateStepFactory {

    fun createBuildSettingsStep(buildTool: BuildTool): Step {
        return when (buildTool) {
            BuildTool.MAVEN -> MavenPomGenerateStep()
            BuildTool.GRADLE -> GradleBuildGenerateStep()
        }
    }

    fun createBuildStructureStep(buildTool: BuildTool): Step {
        return when (buildTool) {
            BuildTool.MAVEN -> MavenStructureGenerateStep()
            BuildTool.GRADLE -> GradleStructureGenerateStep()
        }
    }

}