import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
}

group = "com.github.plugin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    val ktorVersion = "1.5.0"

    implementation(project(":generator"))

    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.0")

    implementation("ch.qos.logback:logback-classic:1.2.3")

    implementation("net.lingala.zip4j:zip4j:2.6.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}