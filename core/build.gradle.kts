import com.akshaw.convention.libNamespace
import com.akshaw.convention.libDefaultConfig
import com.akshaw.convention.libBuildTypes
import com.akshaw.convention.Projects

plugins {
    id("custom.android.library")
}

android {
    libNamespace(Projects.Implementation.CORE)
    libDefaultConfig()
    libBuildTypes(project)
}

dependencies {
    // Android Materials
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    
    // Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    
    //Navigation
    implementation(libs.androidx.navigation.compose)
    
    // Dagger - Hilt
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    implementation(libs.dagger.hilt.navigation.compose)
    
    // Room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    
    // Data Store
    implementation(libs.androidx.datastore)
    
    // Number picker
    implementation(libs.number.picker)
    
    // Kotlin serialization
    implementation(libs.kotlinx.serialization.json)
    
    // Local unit tests
    testImplementation(libs.bundles.test)
    testImplementation(project(Projects.TestImplementation.CORE_TEST))
    
    // Debug implementations
    debugImplementation(libs.androidx.ui.tooling)
}