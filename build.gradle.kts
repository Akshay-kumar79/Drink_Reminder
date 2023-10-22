import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.android.junit5) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.detekt) apply true
}

// Detekt setup
detekt {
    parallel = true
    allRules = true
    autoCorrect = true
    buildUponDefaultConfig = true
    source.setFrom(files(projectDir))
    config.setFrom(file("${rootProject.rootDir}/config/detekt/detekt.yml"))
}

tasks.withType(Detekt::class.java).configureEach {
    include("**/*.kt")
    exclude("**/build/**")
    jvmTarget = JavaVersion.VERSION_17.toString()
    
    reports {
        txt.required.set(false)
        sarif.required.set(false)
        md.required.set(false)
        xml.required.set(false)
        html.required.set(true)
    }
}