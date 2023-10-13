import com.akshaw.convention.buildFeatures
import com.akshaw.convention.configureKotlinAndroid
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-kapt")
                apply("com.google.devtools.ksp")
                apply("com.google.dagger.hilt.android")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("de.mannodermaus.android-junit5")
            }
            
            extensions.configure(LibraryExtension::class.java) {
                configureKotlinAndroid(this)
                buildFeatures()
            }
        }
    }
    
}