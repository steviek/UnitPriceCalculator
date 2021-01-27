import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.4.10"
    id("com.android.library")
    id("kotlin-android-extensions")
    id("dev.icerock.mobile.multiplatform-resources")
}

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    maven(url = "https://dl.bintray.com/icerockdev/moko")
    mavenCentral()
}
kotlin {
    android()
    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.0.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
                implementation("dev.icerock.moko:graphics:0.5.0")
                implementation("dev.icerock.moko:resources:0.13.1")
                implementation("dev.icerock.moko:parcelize:0.5.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:1.2.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.1")
                implementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
                implementation("com.google.truth:truth:1.1")
                implementation("org.robolectric:robolectric:4.4")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.8")
            }
        }
        val iosTest by getting
    }
}
android {
    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
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
val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}
tasks.getByName("build").dependsOn(packForXcode)

multiplatformResources {
    multiplatformResourcesPackage = "com.sixbynine.unitpricecalculator.shared"
}

val extraCompilerArgs = listOf(
    "-Xopt-in=kotlin.RequiresOptIn",
    "-Xopt-in=kotlin.OptIn"
)
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions.freeCompilerArgs += extraCompilerArgs
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile>().all {
    kotlinOptions.freeCompilerArgs += extraCompilerArgs
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon>().all {
    kotlinOptions.freeCompilerArgs += extraCompilerArgs
}