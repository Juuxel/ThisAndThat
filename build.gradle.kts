import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.30"
    idea
    id("fabric-loom") version "0.2.2-SNAPSHOT"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

base {
    archivesBaseName = "ThisAndThat"
}

version = "0.1.3+1.14"

minecraft {
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

repositories {
    maven(url = "https://minecraft.curseforge.com/api/maven") {
        name = "CurseForge"
    }
}

dependencies {
    minecraft("com.mojang:minecraft:1.14")
    mappings("net.fabricmc:yarn:1.14+build.1")

    // Fabric
    modCompile("net.fabricmc:fabric-loader:0.4.6+build.141")
    modCompile("net.fabricmc:fabric:0.2.7+build.127")
    modCompile("net.fabricmc:fabric-language-kotlin:1.3.30-SNAPSHOT")
    compileOnly("net.fabricmc:fabric-language-kotlin:1.3.30-SNAPSHOT")
    modCompile("polyester:Polyester:0.2.1+1.14")
    include("polyester:Polyester:0.2.1+1.14")
}

tasks.getByName<ProcessResources>("processResources") {
    filesMatching("fabric.mod.json") {
        expand(
            mutableMapOf(
                "version" to project.version
            )
        )
    }
}
