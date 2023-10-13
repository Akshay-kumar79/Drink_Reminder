import com.akshaw.convention.libNamespace
import com.akshaw.convention.libDefaultConfig
import com.akshaw.convention.Projects
import com.akshaw.convention.libBuildTypes

plugins {
    id("custom.android.library.compose")
}

android {
    libNamespace(Projects.Implementation.Feature.WATER_PRESENTATION)
    libDefaultConfig()
    libBuildTypes(project)
}

dependencies {
    
    // Module
    implementation(project(Projects.Implementation.CORE))
    implementation(project(Projects.Implementation.CORE_COMPOSE))
    implementation(project(Projects.Implementation.Feature.WATER_DOMAIN))
    
    // Android Materials
    implementation(libs.androidx.material3)
    
    // Compose
    implementation(libs.androidx.compose.ui.tooling.preview)
    
    //Navigation
    implementation(libs.androidx.navigation.compose)
    
    // Dagger - Hilt
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    implementation(libs.dagger.hilt.navigation.compose)
    
    // Number picker
    implementation(libs.number.picker)
    
}