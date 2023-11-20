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
    baseline = file("${rootProject.rootDir}/config/detekt/baseline.xml")
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

val detektProjectBaseline by tasks.registering(io.gitlab.arturbosch.detekt.DetektCreateBaselineTask::class) {
    description = "Overrides current baseline."
    buildUponDefaultConfig.set(true)
    ignoreFailures.set(true)
    parallel.set(true)
    setSource(files(rootDir))
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    baseline.set(file("$rootDir/config/detekt/baseline.xml"))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
}

