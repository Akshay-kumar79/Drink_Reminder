package com.akshaw.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

@Suppress("UnusedReceiverParameter")
internal fun Project.configureKotlinAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        buildFeaturesCompose()
        composeOptions()
    }
}

fun CommonExtension<*, *, *, *, *, *>.buildFeaturesCompose() {
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

fun CommonExtension<*, *, *, *, *, *>.composeOptions() {
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12"
    }
}