import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.11"
    idea
    id("fabric-loom") version "0.1.0-SNAPSHOT"
    id("com.github.johnrengelman.shadow") version "4.0.3"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

base {
    archivesBaseName = "ThisAndThat"
}

repositories {
    maven(url = "https://maven.shadowfacts.net")
}

version = "0.1.1"

minecraft {
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    minecraft("com.mojang:minecraft:18w50a")
    mappings("net.fabricmc:yarn:18w50a.82")
    modCompile("net.fabricmc:fabric-loader:0.3.2.91")
    modCompile("net.fabricmc:fabric:0.1.3.68")
    modCompile("net.fabricmc:fabric-language-kotlin:1.3.10-26")
    compileOnly("net.fabricmc:fabric-language-kotlin:1.3.10-26")

    // Other libraries
    implementation("com.github.anymaker:tnjson:1.2")

    // Other mods
    modCompile(files("../WateredDown/build/libs/WateredDown-0.3.0-dev.jar"))
    modCompile("net.shadowfacts.simplemultipart:SimpleMultipart:0.1.1")
}
