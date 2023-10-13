import com.akshaw.convention.libNamespace
import com.akshaw.convention.libDefaultConfig
import com.akshaw.convention.Projects
import com.akshaw.convention.libBuildTypes

plugins {
    id("custom.android.library")
}

android {
    libNamespace(Projects.Implementation.Feature.WATER_DOMAIN)
    libDefaultConfig()
    libBuildTypes(project)
}

dependencies {
    
    // Module
    implementation(project(Projects.Implementation.CORE))
    
    // Dagger - Hilt
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    implementation(libs.dagger.hilt.navigation.compose)
    
    // Local unit tests
    testImplementation(libs.bundles.test)
    testImplementation(project(Projects.TestImplementation.CORE_TEST))

}