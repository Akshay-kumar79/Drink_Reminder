package com.akshaw.convention

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>
) {
    commonExtension.apply {
        commonSdk()
        compileOptions()
        kotlinOptions()
        packagingOptions()
    }
}

fun CommonExtension<*, *, *, *, *>.commonSdk() {
    defaultConfig.minSdk = Android.Sdk.MIN
    compileSdk = Android.Sdk.COMPILE
}

fun CommonExtension<*, *, *, *, *>.compileOptions() {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

fun CommonExtension<*, *, *, *, *>.kotlinOptions() {
    (this as ExtensionAware).extensions.configure<KotlinJvmOptions>(Android.Extension.KOTLIN_OPTIONS) {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

fun CommonExtension<*, *, *, *, *>.packagingOptions() {
    packagingOptions {
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

fun BaseAppModuleExtension.applicationSigningConfigs() {
    signingConfigs {
        create("release") {
//            storeFile = file("C:\\Users\\Khushbu kumari\\Desktop\\drink_reminder_test.jks")
            storePassword = "qazxswedc"
            keyAlias = "key0"
            keyPassword = "qazxswedc"
        }
    }
}


fun BaseAppModuleExtension.applicationBuildTypes() {
    buildTypes {
        named("debug") {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        named("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
//            signingConfig = signingConfigs.getByName("release")
        }
    }
}

fun BaseAppModuleExtension.appDefaultConfig() {
    defaultConfig {
        applicationId = Android.APPLICATION_ID
        versionCode = 1
        versionName = "1.0.0"
        
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