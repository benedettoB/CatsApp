plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    //alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ksp.plug)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "org.benedetto.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.coroutines)
    implementation(libs.okhttp)
    implementation(libs.gson)
    // Arch Components
    //kapt(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.hilt.android)
    //kapt(libs.hilt.compiler)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
}