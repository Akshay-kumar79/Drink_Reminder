import com.akshaw.convention.Projects
import com.akshaw.convention.appDefaultConfig
import com.akshaw.convention.applicationBuildTypes
import com.akshaw.convention.appNamespace
import com.akshaw.convention.appSigningConfigs

plugins {
    id("custom.android.application")
    id("dagger.hilt.android.plugin")
}

android {
    appNamespace()
    appDefaultConfig(project)
    appSigningConfigs(project)
    applicationBuildTypes()
    
    lint {
        baseline = file("${project.rootDir}/config/lint/baseline.xml")
        htmlOutput = file("${project.parent?.buildDir}/reports/lint/lint.html")
        textReport = false
        xmlReport = false
        checkDependencies = true
    }
}

dependencies {
    
    // Modules
    implementation(project(Projects.Implementation.CORE))
    implementation(project(Projects.Implementation.CORE_COMPOSE))
    implementation(project(Projects.Implementation.Feature.ONBOARDING_PRESENTATION))
    implementation(project(Projects.Implementation.Feature.WATER_PRESENTATION))
    implementation(project(Projects.Implementation.Feature.SETTINGS_PRESENTATION))
    
    // Android Materials
    implementation(libs.androidx.material3)
    
    // Dagger - Hilt
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    implementation(libs.dagger.hilt.navigation.compose)
    
}