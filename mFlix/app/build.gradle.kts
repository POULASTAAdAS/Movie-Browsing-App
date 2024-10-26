import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.poulastaa.mflix"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.poulastaa.mflix"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        val clientId = gradleLocalProperties(rootDir, providers).getProperty("CLIENT_ID")
        val baseUrl = gradleLocalProperties(rootDir, providers).getProperty("BASE_URL")
        val apiBaseUrl = gradleLocalProperties(rootDir, providers).getProperty("API_BASE_URL")
        val token = gradleLocalProperties(rootDir, providers).getProperty("API_TOKEN")
        val imageUrl = gradleLocalProperties(rootDir, providers).getProperty("API_IMAGE_URL")

        release {
            buildConfigField("String", "GOOGLE_CLIENT_ID", "\"$clientId\"")
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            buildConfigField("String", "API_BASE_URL", "\"$apiBaseUrl\"")
            buildConfigField("String", "API_TOKEN", "\"$token\"")
            buildConfigField("String", "IMAGE_URL", "\"$imageUrl\"")

            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            buildConfigField("String", "GOOGLE_CLIENT_ID", "\"$clientId\"")
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            buildConfigField("String", "API_BASE_URL", "\"$apiBaseUrl\"")
            buildConfigField("String", "API_TOKEN", "\"$token\"")
            buildConfigField("String", "IMAGE_URL", "\"$imageUrl\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.material3WindowSizeClass)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.dagger.hilt)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.dagger.hilt.compiler)
    kapt(libs.hilt.compiler)

    implementation(libs.okhttp.url.connection)
    implementation(libs.navigation.compose)
    implementation(libs.core.splash.screen)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.credentials)
    implementation(libs.credentialsPlayServicesAuth)
    implementation(libs.googleid)

    implementation(libs.gson)

    implementation(libs.datastore.preferences)

    implementation(libs.coil.compose)
}