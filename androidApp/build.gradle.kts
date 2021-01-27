plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") version "1.4.10"
    id("kotlin-android-extensions")
}

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}
dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
}
android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.unitpricecalculator"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}