import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.11"
    idea
    id("fabric-loom") version "0.1.0-SNAPSHOT"
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
    mavenCentral()
    maven(url = "https://jitpack.io")
    maven(url = "http://server.bbkr.space:8081/artifactory/libs-snapshot/") // Cotton
}

version = "0.2.0"

minecraft {
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

configurations {
    create("shadow")
    this["compile"].extendsFrom(this["shadow"])
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    minecraft("com.mojang:minecraft:19w03c")
    mappings("net.fabricmc:yarn:19w03c.1")
    modCompile("net.fabricmc:fabric-loader:0.3.2.96")
    modCompile("net.fabricmc:fabric:0.1.4.77")
    modCompile("net.fabricmc:fabric-language-kotlin:1.3.10-29")

    // Other libraries
    add("shadow", "com.github.anymaker:tnjson:1.2")
    add("shadow", "com.github.Juuxel:Jay:e4e5d72318")

    // Other mods
    modCompile(files("../WateredDown/build/libs/WateredDown-0.3.1-dev.jar"))
    // TODO: Add back on update
//    modCompile("net.shadowfacts.simplemultipart:SimpleMultipart:0.1.2")
    modCompile(files("../Tagger/build/libs/Tagger-0.1.0-dev.jar"))
    modCompile("io.github.cottonmc:cotton:0.0.2+19w02a-SNAPSHOT")
}

tasks.withType<Jar> {
    from(configurations["shadow"].asFileTree.files.map { zipTree(it) })
}
