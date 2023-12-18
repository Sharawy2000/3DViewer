plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.a3dviewer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.a3dviewer"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildToolsVersion = "34.0.0"
}

dependencies {

    //noinspection GradleCompatible,GradleCompatible,GradleCompatible
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.ar.sceneform.ux:sceneform-ux:1.17.1")
    implementation ("com.google.ar.sceneform:core:1.17.1")
    implementation ("com.google.ar.sceneform:assets:1.17.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.appcompat:appcompat:1.3.1")
    implementation ("com.github.jackrzhang:gpu-image:2.6.0")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
    implementation ("com.google.android.material:material:1.4.0")
    implementation ("androidx.camera:camera-core:1.0.0-rc02")
    implementation ("androidx.camera:camera-camera2:1.0.0-rc02")
    implementation ("androidx.camera:camera-lifecycle:1.0.0-rc02")
    implementation ("androidx.camera:camera-view:1.0.0-alpha35")
    implementation ("androidx.camera:camera-view:1.0.0-alpha16")


}