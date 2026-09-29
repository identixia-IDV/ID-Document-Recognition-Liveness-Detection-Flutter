group = "com.identixia.document_reader_sdk"
version = "1.0-SNAPSHOT"


buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // AGP only — do not apply Kotlin Gradle Plugin (Built-in Kotlin / AGP 9+).
        classpath("com.android.tools.build:gradle:8.9.1")
    }
}


allprojects {
    repositories {
        google()
        mavenCentral()
    }
}


plugins {
    id("com.android.library")
}


android {
    namespace = "com.identixia.document_reader_sdk"


    compileSdk = 36


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }


    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/kotlin")
        }
    }


    defaultConfig {
        minSdk = 24
    }
}


// Built-in Kotlin (AGP 9+ / Flutter consumer) — no org.jetbrains.kotlin.android apply.
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}


dependencies {
    implementation("androidx.exifinterface:exifinterface:1.3.7")
    val bundledAar = file("libs/documentreadersdk.aar")
    when {
        bundledAar.exists() -> implementation(files(bundledAar))
        findProject(":libdocsdk") != null ->
            // Example app: example/android/libdocsdk via settings.gradle.kts
            implementation(project(":libdocsdk"))
        else -> {
            val aar = file("libs/documentreadersdk.aar")
            aar.parentFile.mkdirs()
            val url = java.net.URI(
                "https://github.com/identixia-IDV/ID-Document-Recognition-Liveness-Detection-Android/releases/download/v1.0.0/documentreadersdk.aar"
            ).toURL()
            try {
                url.openStream().use { input -> aar.outputStream().use { input.copyTo(it) } }
            } catch (e: Exception) {
                throw GradleException(
                    "Missing documentreadersdk.aar.\n" +
                        "Demo: example/android/libdocsdk/documentreadersdk.aar\n" +
                        "Own app: apply https://raw.githubusercontent.com/identixia-IDV/ID-Document-Recognition-Liveness-Detection-Android/v1.0.0/install.gradle\n" +
                        "Download failed: ${e.message}"
                )
            }
            implementation(files(aar))
        }
    }
}
