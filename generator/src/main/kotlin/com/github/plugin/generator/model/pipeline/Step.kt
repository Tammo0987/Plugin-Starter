package com.github.plugin.generator.model.pipeline

import com.github.plugin.generator.model.Plugin
import java.nio.file.Path

abstract class Step {

    abstract fun process(plugin: Plugin, workingDirectory: Path)

}