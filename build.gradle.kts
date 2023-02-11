import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.dokka") version "1.7.20"
    kotlin("jvm") version "1.7.10"
    application
}

group = "me.lukas"
version = "1.1"

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
    implementation("net.logstash.logback:logstash-logback-encoder:7.2") // ?
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")
    implementation("ch.qos.logback:logback-classic:1.4.4")

    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.2")
    testImplementation("org.junit-pioneer:junit-pioneer:1.7.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.1") // for parameterized tests
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed","failed")
        showStackTraces = true
    }
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
    mainClass.set("run.Run")
}