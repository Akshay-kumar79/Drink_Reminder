pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Drink Reminder"
include(":app")
include(":onboarding")
include(":water")
include(":onboarding:onboarding_presentation")
include(":onboarding:onboarding_domain")
include(":core")
include(":water:water_data")
include(":water:water_domain")
include(":water:water_presentation")
include(":core-test")
include(":core-compose")
include(":settings")
include(":settings:settings-presentation")
