@file:Suppress("unused")

package com.akshaw.convention

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import java.io.File

@Suppress("UnusedReceiverParameter")
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        commonSdk()
        compileOptions()
        kotlinOptions()
        packagingOptions()
    }
}

fun CommonExtension<*, *, *, *, *, *>.buildFeatures() {
    buildFeatures {
        buildConfig = true
    }
}

fun CommonExtension<*, *, *, *, *, *>.commonSdk() {
    defaultConfig.minSdk = Android.Sdk.MIN
    compileSdk = Android.Sdk.COMPILE
}

fun CommonExtension<*, *, *, *, *, *>.compileOptions() {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

fun CommonExtension<*, *, *, *, *, *>.kotlinOptions() {
    (this as ExtensionAware).extensions.configure<KotlinJvmOptions>(Android.Extension.KOTLIN_OPTIONS) {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

fun CommonExtension<*, *, *, *, *, *>.packagingOptions() {
    packaging {
        resources {
            excludes += mutableSetOf(
                "/META-INF/LICENSE.md",
                "/META-INF/LICENSE-notice.md"
            )
        }
    }
}

fun BaseAppModuleExtension.appNamespace() {
    namespace = Android.APPLICATION_ID
}

fun LibraryExtension.libNamespace(moduleName: String) {
    namespace = Android.APPLICATION_ID + "." + moduleName.namespace()
}

internal fun String.namespace() = this.substringAfterLast(":").replace("-", ".")

fun BaseAppModuleExtension.appSigningConfigs(project: Project) {
    signingConfigs {
        create("beta") {
            storeFile = File(project.findLocalProperty(Config.LocalProperties.SingingConfig.STORE_FILE).toString())
            storePassword = project.findLocalProperty(Config.LocalProperties.SingingConfig.STORE_PASSWORD).toString()
            keyAlias = project.findLocalProperty(Config.LocalProperties.SingingConfig.KEY_ALIAS).toString()
            keyPassword = project.findLocalProperty(Config.LocalProperties.SingingConfig.KEY_PASSWORD).toString()
        }
        
        create("release") {
            storeFile = File(project.findLocalProperty(Config.LocalProperties.SingingConfig.STORE_FILE).toString())
            storePassword = project.findLocalProperty(Config.LocalProperties.SingingConfig.STORE_PASSWORD).toString()
            keyAlias = project.findLocalProperty(Config.LocalProperties.SingingConfig.KEY_ALIAS).toString()
            keyPassword = project.findLocalProperty(Config.LocalProperties.SingingConfig.KEY_PASSWORD).toString()
        }
    }
}


fun BaseAppModuleExtension.applicationBuildTypes() {
    buildTypes {
        named("debug") {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
        }
        create("beta") {
            applicationIdSuffix = ".beta"
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("beta")
        }
        named("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

fun LibraryExtension.libBuildTypes(project: Project) {
    buildTypes {
        configureEach {
            buildConfigField("String", "VERSION_NAME", project.findVersionProperty(Config.VersionProperties.Version.NAME).asString())
        }
        named("debug") {
            isMinifyEnabled = false
        }
        create("beta") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        named("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

fun BaseAppModuleExtension.appDefaultConfig(project: Project) {
    defaultConfig {
        applicationId = Android.APPLICATION_ID
        versionName = project.findVersionProperty(Config.VersionProperties.Version.NAME)
        versionCode = project.findVersionProperty(Config.VersionProperties.Version.CODE).toInt()
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
}

fun LibraryExtension.libDefaultConfig() {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}


object Android {
    
    const val APPLICATION_ID = "com.akshaw.drinkreminder"
    
    object Extension {
        const val KOTLIN_OPTIONS = "kotlinOptions"
    }
    
    object Sdk {
        const val MIN = 26
        const val TARGET = 34
        const val COMPILE = 34
    }
}