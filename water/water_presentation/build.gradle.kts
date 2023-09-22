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
    
    // Number picker
    implementation(libs.number.picker)
    
    // Local unit tests
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.arch.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.mockk)
    debugImplementation(libs.androidx.ui.test.manifest)
    
    // Instrumentation tests
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.dagger.hilt.android.compiler)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.ext.junit)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.arch.core.testing)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.androidx.test.core.ktx)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.mockwebserver)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.androidx.runner)
    
    // Debug implementations
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}