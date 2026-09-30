group = "com.identixia.document_reader_sdk"
version = "1.0-SNAPSHOT"

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.9.1")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("${project.projectDir}/build/identixia-maven") }
    }
}

plugins {
    id("com.android.library")
}

android {
    namespace = "com.identixia.document_reader_sdk"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    implementation("androidx.exifinterface:exifinterface:1.3.7")

    fun useLocalMavenDocAar(source: java.io.File) {
        val maven = file("build/identixia-maven/com/identixia/documentreadersdk/1.0.0")
        maven.mkdirs()
        source.copyTo(maven.resolve("documentreadersdk-1.0.0.aar"), overwrite = true)
        maven.resolve("documentreadersdk-1.0.0.pom").writeText(
            """
            <project>
              <modelVersion>4.0.0</modelVersion>
              <groupId>com.identixia</groupId>
              <artifactId>documentreadersdk</artifactId>
              <version>1.0.0</version>
              <packaging>aar</packaging>
            </project>
            """.trimIndent()
        )
        implementation("com.identixia:documentreadersdk:1.0.0")
    }

    val bundledAar = file("libs/documentreadersdk.aar")
    when {
        findProject(":libdocsdk") != null -> implementation(project(":libdocsdk"))
        bundledAar.exists() -> useLocalMavenDocAar(bundledAar)
        else -> {
            val aar = file("libs/documentreadersdk.aar")
            aar.parentFile.mkdirs()
            try {
                ant.invokeMethod(
                    "get",
                    mapOf(
                        "src" to "https://github.com/identixia-IDV/ID-Document-Recognition-Liveness-Detection-Android/releases/latest/download/documentreadersdk.aar",
                        "dest" to aar.absolutePath,
                    ),
                )
            } catch (e: Exception) {
                throw GradleException(
                    "Missing documentreadersdk.aar.\n" +
                        "Demo: example/android/libdocsdk/documentreadersdk.aar\n" +
                        "Own app: apply install.gradle from the Android product repo.\n" +
                        "Download failed: ${e.message}"
                )
            }
            useLocalMavenDocAar(aar)
        }
    }
}
