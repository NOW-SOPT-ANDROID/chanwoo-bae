import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
}

val properties = Properties()
properties.load(rootProject.file("local.properties").inputStream())

android {
    namespace = "com.sopt.now"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.sopt.now"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        val authBaseUrl = (properties["base.url"] as? String)?.trim('"') ?: ""
        val reqresBaseUrl = (properties["reqres.base.url"] as? String)?.trim('"') ?: ""

        buildConfigField("String", "AUTH_BASE_URL", "\"$authBaseUrl\"")
        buildConfigField("String", "REQRES_BASE_URL", "\"$reqresBaseUrl\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        dataBinding = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data-remote"))
    implementation(project(":data-local"))
    implementation(project(":data"))
    implementation(project(":core-ui"))
    implementation(project(":feature"))

    implementation(libs.security.crypto)

    // retrofit
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    // define a BOM and its version
    implementation(platform(libs.okhttp.bom))

    // define any required OkHttp artifacts without version
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // dagger hilt
    implementation(libs.dagger.hilt)

    kapt(libs.dagger.hilt.compiler)
}
