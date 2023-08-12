import com.akshaw.convention.Android
import com.akshaw.convention.buildFeatures
import com.akshaw.convention.composeOptions
import com.akshaw.convention.configureKotlinAndroid
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-kapt")
            }
            
            extensions.configure(ApplicationExtension::class.java) {
                configureKotlinAndroid(this)
                targetSdk()
                buildFeatures()
                composeOptions()
            }
            
        }
    }
}

fun ApplicationExtension.targetSdk() {
    defaultConfig.targetSdk = Android.Sdk.TARGET
}


