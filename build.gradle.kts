import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.dokka") version "1.7.20"
    kotlin("jvm") version "1.7.10"
    application
}

group = "me.lukas"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

buildscript {
    dependencies {
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.7.20")
    }
}

apply(plugin="org.jetbrains.dokka")

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks {
    val jar = register<Jar>("Fat Jar") {
        dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources")) // We need this for Gradle optimization to work
        archiveClassifier.set("standalone") // Naming the jar
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest { attributes(mapOf("Main-Class" to application.mainClass)) } // Provided we set it up in the application plugin configuration
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } +
                sourcesMain.output
        from(contents)
    }

    build {
        dependsOn(jar) // Create a .jar every build
        dependsOn(dokkaHtml) // Create Doc's every build
    }
}

tasks.getByName<JavaExec>("run") {
    standardInput = System.`in`
}



tasks.dokkaHtml.configure {
    outputDirectory.set(buildDir.resolve("Documentation"))

    dokkaSourceSets {
        configureEach{
            includeNonPublic.set(true)
        }
    }
}
application {
    mainClass.set("MainKt")
}