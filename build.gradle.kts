import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.11"
    idea
    id("fabric-loom") version "0.2.0-SNAPSHOT"
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
//    maven(url = "https://jitpack.io")
    maven(url = "http://server.bbkr.space:8081/artifactory/libs-snapshot/") // Cotton
    maven(url = "https://repo.elytradev.com") // Jankson

    flatDir {
        dirs("libs")
    }
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

fun DependencyHandler.modFileCompile(name: String) {
    require(rootProject.file("libs/$name.jar").exists()) {
        "Dependency file libs/$name.jar doesn't exist!\n" +
            "Add the dev jar into libs."
    }

    modCompile(group = "", name = name)
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    minecraft("com.mojang:minecraft:19w05a")
    mappings("net.fabricmc:yarn:19w05a.6")
    modCompile("net.fabricmc:fabric-loader:0.3.2.96")
    modCompile("net.fabricmc:fabric:0.1.5.88")
    modCompile("net.fabricmc:fabric-language-kotlin:1.3.10-29")

    // Other libraries
    add("shadow", "blue.endless:jankson:1.1.1-31")

    // Other mods
    // TODO: Switch to non-dev JAR + modFileCompile when fixed
    // Issue: FabricMC/fabric-loom#56
    implementation(group = "", name = "WateredDown-0.3.2+19w05a-dev")
    // TODO: Add back on update
//    modCompile("net.shadowfacts.simplemultipart:SimpleMultipart:0.1.2")
    // TODO: Bundle Cotton with a J-in-J?
    modCompile("io.github.cottonmc:cotton:0.0.2+19w05a-SNAPSHOT")
}

tasks.withType<Jar> {
    from(configurations["shadow"].asFileTree.files.map(::zipTree))
}
