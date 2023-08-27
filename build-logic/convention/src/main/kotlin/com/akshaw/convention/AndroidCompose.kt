package com.akshaw.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project


internal fun Project.configureKotlinAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>
) {
    commonExtension.apply {
        buildFeatures()
        composeOptions()
    }
}

fun CommonExtension<*, *, *, *, *>.buildFeatures() {
    buildFeatures.compose = true
}

fun CommonExtension<*, *, *, *, *>.composeOptions() {
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.8"
    }
}