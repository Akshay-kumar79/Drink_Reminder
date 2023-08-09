package com.akshaw.convention

import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *>
) {
    commonExtension.apply {
        commonSdk()
        buildFeatures()
        compileOptions()
        kotlinOptions()
        packagingOptions()
        composeOptions()
    }
}

fun CommonExtension<*, *, *, *>.commonSdk() {
    defaultConfig.minSdk = Android.Sdk.MIN
    compileSdk = Android.Sdk.COMPILE
}

fun CommonExtension<*, *, *, *>.buildFeatures() {
    buildFeatures.compose = true
}

fun CommonExtension<*, *, *, *>.compileOptions() {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

fun CommonExtension<*, *, *, *>.kotlinOptions() {
    (this as ExtensionAware).extensions.configure<KotlinJvmOptions>(Android.Extension.KOTLIN_OPTIONS) {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

fun CommonExtension<*, *, *, *>.packagingOptions() {
    packagingOptions {
        resources {
            excludes += mutableSetOf(
                "/META-INF/LICENSE.md",
                "/META-INF/LICENSE-notice.md"
            )
        }
    }
}

fun CommonExtension<*, *, *, *>.composeOptions() {
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
}

fun BaseAppModuleExtension.appNamespace() {
    namespace = "com.akshaw.drinkreminder"
}

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
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
//            signingConfig = signingConfigs.getByName("release")
        }
    }
}

fun BaseAppModuleExtension.appDefaultConfig() {
    defaultConfig {
        applicationId = "com.akshaw.drinkreminder"
        versionCode = 1
        versionName = "1.0.0"
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
}


object Android {
    
    object Extension {
        const val KOTLIN_OPTIONS = "kotlinOptions"
    }
    
    object Sdk {
        const val MIN = 24
        const val TARGET = 33
        const val COMPILE = 33
    }
}