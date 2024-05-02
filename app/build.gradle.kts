import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlinx.serialization)
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

        val reqresBaseUrl = properties["reqres.base.url"] as? String ?: ""

        buildConfigField("String", "AUTH_BASE_URL", "\"${findProperty("base.url") ?: ""}\"")
        buildConfigField("String", "REQRES_BASE_URL", "\"${reqresBaseUrl}\"")
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
        dataBinding = true
        buildConfig = true
    }
}

dependencies {
    // coil
    implementation(libs.coil)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    // define a BOM and its version
    implementation(platform(libs.okhttp.bom))

    // define any required OkHttp artifacts without version
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    // jetpack navi
    implementation(libs.bundles.jetpack.navi)
    // sharedPreference crypto
    implementation(libs.security.crypto)
    // google
    implementation(libs.material)
    // ktx (by viewModels)
    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)
    // dagger hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
    // 기초 androidx 라이브러리 ("core-ktx", "constraintlayout", "appcompat", "activity")
    implementation(libs.bundles.androidx)
}
