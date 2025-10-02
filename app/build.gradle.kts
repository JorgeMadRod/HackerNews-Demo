plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id ("kotlin-parcelize")
    id ("kotlin-kapt")
}

android {
    namespace = "com.jmadrigal.hackernews"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.jmadrigal.hackernews"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.9.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.9.0")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.9.0")
    // Lifecycle
    implementation( "androidx.fragment:fragment-ktx:1.8.9")
    implementation ("androidx.activity:activity-ktx:1.11.0")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.4")
    // GSON
    implementation ("com.google.code.gson:gson:2.13.2")
    // OkHttp
    implementation ("com.squareup.okhttp3:okhttp:5.1.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.1.0")
    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:3.0.0")
    // Gson converter
    implementation ("com.squareup.retrofit2:converter-gson:3.0.0")
    // Hilt
    implementation ("com.google.dagger:hilt-android:2.57.2")
    kapt ("com.google.dagger:hilt-android-compiler:2.57.2")
    kapt ("androidx.hilt:hilt-compiler:1.3.0")
    // Room
    implementation("androidx.room:room-runtime:2.8.1")
    kapt("androidx.room:room-compiler:2.8.1")
    implementation("androidx.room:room-ktx:2.8.1")
    // Shimmer
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}