plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("ru.practicum.android.diploma.plugins.developproperties")
    id("org.jetbrains.kotlin.plugin.serialization") version libs.versions.kotlin.get()
    id("kotlin-parcelize")
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.devtoolsKsp)
}

android {
    namespace = "ru.practicum.android.diploma"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ru.practicum.android.diploma"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(type = "String", name = "API_ACCESS_TOKEN", value = "\"${developProperties.apiAccessToken}\"")
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.appCompat)

    // UI layer libraries
    implementation(libs.ui.material)
    implementation(libs.ui.constraintLayout)

    // region Unit tests
    testImplementation(libs.unitTests.junit)
    // endregion

    // region UI tests
    androidTestImplementation(libs.uiTests.junitExt)
    androidTestImplementation(libs.uiTests.espressoCore)
    // endregion

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))
    implementation(libs.material3)
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    debugImplementation(libs.ui.tooling)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.activity.compose)

    // Navigation Compose
    implementation(libs.navigation.compose)

    // Koin
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.android)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.kotlinx.collections.immutable)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // region Gson
    implementation(libs.gson)
    // endregion

    // region Gson
    implementation(libs.gson)
    // endregion

    // region compose
    implementation(libs.ui)
    implementation(libs.material3)
    debugImplementation(libs.ui.tooling)
    implementation(libs.runtime)
    implementation(libs.navigation.compose)
    // endregion

    // region Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)
    implementation(libs.room.ktx)
    // endregion
}
