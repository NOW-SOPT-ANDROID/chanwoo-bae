pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NOW SOPT Android"
include(":app")
include(":domain")
include(":data")
include(":data-remote")
include(":data-local")
include(":core-ui")
include(":feature")
