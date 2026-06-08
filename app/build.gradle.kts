plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.schoolmanager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.schoolmanager"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.fragment)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation (libs.androidx.appcompat.v161)
    implementation (libs.material.v1110)
    implementation (libs.androidx.constraintlayout.v214)

    // Lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    // Room
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation(libs.room.common.jvm)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.recyclerview)
    annotationProcessor ("androidx.room:room-compiler:2.6.1")
    implementation (libs.androidx.room.ktx)// pour LiveData support

    // ViewModel + LiveData
    implementation (libs.androidx.lifecycle.viewmodel.v270)

    implementation (libs.androidx.lifecycle.livedata)

    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // RecyclerView + CardView
    implementation (libs.androidx.recyclerview.v132)
    implementation (libs.androidx.cardview)

    // Navigation Drawer
    implementation (libs.drawerlayout)

    // Image loading (optionnel mais pratique)
    implementation (libs.glide)
    annotationProcessor (libs.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}