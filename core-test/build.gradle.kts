import com.akshaw.convention.libNamespace
import com.akshaw.convention.libDefaultConfig
import com.akshaw.convention.Projects
import com.akshaw.convention.libBuildTypes

plugins {
    id("custom.android.library")
}

android {
    libNamespace(Projects.TestImplementation.CORE_TEST)
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
    
}