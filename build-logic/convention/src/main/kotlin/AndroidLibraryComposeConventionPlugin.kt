import com.akshaw.convention.configureKotlinAndroidCompose
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("custom.android.library")
            }
            
            extensions.configure(LibraryExtension::class.java) {
                configureKotlinAndroidCompose(this)
            }
        }
    }
}