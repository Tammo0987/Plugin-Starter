package com.github.plugin.generator.service.step.io

import com.github.plugin.generator.model.Plugin
import com.github.plugin.generator.model.pipeline.Step
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.writeBytes

class FileCreationStep(private val path: String, private val content: ByteArray) : Step() {

    @ExperimentalPathApi
    override fun process(plugin: Plugin) = Path(this.path).writeBytes(this.content)

}