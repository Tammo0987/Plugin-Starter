import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
}

group = "com.github.plugin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()

    maven {
        name = "jcenter"
        url = uri("https://jcenter.bintray.com")
    }
}

dependencies {
    implementation("org.redundent:kotlin-xml-builder:1.7.2")
    implementation("com.squareup:kotlinpoet:1.7.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}