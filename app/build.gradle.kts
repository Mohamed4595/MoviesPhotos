plugins {
    id("com.android.application")
    kotlin("android")
    kotlin(KotlinPlugins.serialization) version Kotlin.version
    id("kotlin-kapt")
}

android {
    compileSdk = Android.compileSdk
    @Suppress("UnstableApiUsage")
    buildToolsVersion = Android.buildTools
    namespace = Android.appId

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName
        testInstrumentationRunner = "com.eplmatches.app.CustomTestRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
        getByName("debug") {
            isDebuggable = true
        }
    }
    @Suppress("UnstableApiUsage")
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.kotlinCompilerComposeVersion
    }
    packaging {
        resources {
            excludes += setOf("META-INF/AL2.0", "META-INF/LGPL2.1")
        }
    }
}

dependencies {

    implementation(project(Modules.core))
    implementation(project(Modules.components))
    implementation(project(Modules.constants))
    implementation(project(Modules.splash))

    implementation(project(Modules.moviesListDomain))
    implementation(project(Modules.moviesListData))
    implementation(project(Modules.moviesListInteractors))
    implementation(project(Modules.moviesListPresentation))

    implementation(project(Modules.fullScreenViewPresentation))

    implementation(Coil.coil)

    implementation(Accompanist.animations)

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.lifecycleVmKtx)

    implementation(Compose.activity)
    implementation(Compose.ui)
    implementation(Compose.material)
    implementation(Compose.tooling)
    implementation(Compose.util)
    implementation(Compose.navigation)
    implementation(Koin.navigation)

    implementation(Google.material)

    implementation(Koin.android)
    implementation(Koin.core)
    implementation(Koin.compose)


    implementation(Kotlinx.serialization)
    implementation(Ktor.core)
    implementation(Ktor.clientSerialization)
    implementation(Ktor.android)
    implementation(Room.roomKtx)
    implementation(Room.roomCommon)
    kapt(Room.roomCompiler)
    implementation(Room.roomRuntime)

    testImplementation(Google.truth)
    testImplementation(Junit.junit4)
    testImplementation(Ktor.ktorClientMock)
    testImplementation(Ktor.clientSerialization)

    androidTestImplementation(Google.truth)
    androidTestImplementation(AndroidXTest.runner)
    androidTestImplementation(ComposeTest.uiTestJunit4)
    debugImplementation(ComposeTest.uiTestManifest)
    androidTestImplementation(Junit.junit4)
}