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
}

version = "0.2.0"

minecraft {
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

configurations {
    create("provided")
    this["compile"].extendsFrom(this["provided"])
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
    add("provided", "com.github.anymaker:tnjson:1.2")

    // Other mods
    modCompile(files("../WateredDown/build/libs/WateredDown-0.3.0-dev.jar"))
    modCompile("net.shadowfacts.simplemultipart:SimpleMultipart:0.1.1")
}

tasks.withType<Jar> {
    from(configurations["provided"].asFileTree.files.map { zipTree(it) })
}
