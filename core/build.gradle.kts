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
    
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    
    // Dagger - Hilt
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    implementation(libs.dagger.hilt.navigation.compose)
    
    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    
    // Data Store
    implementation(libs.androidx.datastore)
    
    // Kotlin serialization
    implementation(libs.kotlinx.serialization.json)
    
    // Local unit tests
    testImplementation(libs.bundles.test)
    testImplementation(project(Projects.TestImplementation.CORE_TEST))
    
}