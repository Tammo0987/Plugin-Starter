package com.github.plugin.generator.service.step.io

import com.github.plugin.generator.model.Plugin
import com.github.plugin.generator.model.pipeline.Step
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.notExists

class DirectoryCreationStep(private val path: String) : Step() {

    @ExperimentalPathApi
    override fun process(plugin: Plugin, workingDirectory: Path) {
        val path = workingDirectory.resolve(Path(this.path))

        if (path.notExists()) {
            path.createDirectories()
        }
    }
}