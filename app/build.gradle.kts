plugins {
    id("com.android.application")

    kotlin("android")
    kotlin("kapt")


    id("androidx.navigation.safeargs")
    id("de.undercouch.download")
}
//apply plugin: "androidx.navigation.safeargs.kotlin"
android {
    namespace = "com.example.thirdeye"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.thirdeye"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled =true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        dataBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    aaptOptions {
        noCompress += "tflite"
    }
    sourceSets {
        named("main") {
            assets.srcDirs("src/main/assets", "src/main/assets/")
        }
    }


    buildFeatures {
        mlModelBinding = true
        viewBinding = true
    }
    androidResources{
        noCompress("tflite")
    }

        packagingOptions {
            pickFirst ("META-INF/DEPENDENCIES")
            pickFirst ("META-INF/DEPENDENCIES")
        }

}
project.extra["ASSET_DIR"] = projectDir.toString() + "/src/main/assets"

dependencies {

    implementation(kotlin("stdlib"))
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.31")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0")
    implementation("com.google.android.material:material:1.6.1")
// App compat and UI things
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
//    implementation ("org.mongodb:mongodb-driver-sync:4.2.3")
//    implementation("org.mongodb:mongodb-driver:3.4.2")
    implementation(files("libs/mongo-java-driver-3.4.0-SNAPSHOT.jar"))


    implementation("androidx.localbroadcastmanager:localbroadcastmanager:1.0.0")
    implementation("androidx.fragment:fragment-ktx:1.5.4")
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    implementation ("org.apache.httpcomponents:httpclient:4.5.13")


// Navigation library
    val nav_version = "2.3.5"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

// CameraX core library
    val camerax_version = "1.1.0"
    implementation("androidx.camera:camera-core:$camerax_version")

// CameraX Camera2 extensions
    implementation("androidx.camera:camera-camera2:$camerax_version")

// CameraX Lifecycle library
    implementation("androidx.camera:camera-lifecycle:$camerax_version")

// CameraX View class
    implementation("androidx.camera:camera-view:$camerax_version")

// WindowManager
    implementation("androidx.window:window:1.0.0-alpha09")

// Unit testing
    testImplementation("androidx.test.ext:junit:1.1.3")
    testImplementation("androidx.test:rules:1.4.0")
    testImplementation("androidx.test:runner:1.4.0")
    testImplementation("androidx.test.espresso:espresso-core:3.4.0")
    testImplementation("org.robolectric:robolectric:4.4")

// Instrumented testing
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test:core:1.4.0")
    androidTestImplementation("androidx.test:rules:1.4.0")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    implementation("com.google.mediapipe:tasks-vision:0.10.2")


    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation ("com.intuit.sdp:sdp-android:1.1.0")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("com.google.mlkit:translate:17.0.0")
    implementation("com.google.mlkit:language-id:17.0.0")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.1.0")
    implementation("org.tensorflow:tensorflow-lite-gpu:2.3.0")
    implementation("com.google.mlkit:object-detection-common:18.0.0")
    implementation("com.google.mlkit:object-detection:17.0.0")
    implementation("com.google.mlkit:object-detection-custom:17.0.1")
    implementation("com.google.mlkit:image-labeling:17.0.7")

    testImplementation("junit:junit:4.12")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0") // Updated version
//    androidTestImplementation("androidx.test.espresso:espresso-idling-resource:3.4.0") // Updated version
//    androidTestImplementation("androidx.test.ext:junit:1.1.0")
    implementation ("com.google.android.gms:play-services-tasks:17.2.1")
    implementation("com.google.android.gms:play-services-mlkit-text-recognition:19.0.0")
    implementation ("com.google.android.gms:play-services-mlkit-text-recognition-devanagari:16.0.0")
    implementation ("com.google.android.gms:play-services-mlkit-text-recognition-chinese:16.0.0")
    implementation ("com.google.android.gms:play-services-mlkit-text-recognition-japanese:16.0.0")
    implementation ("com.google.android.gms:play-services-mlkit-text-recognition-korean:16.0.0")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.1.0")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.1.0")
    implementation("org.tensorflow:tensorflow-lite:0.0.0-nightly")
    implementation("com.otaliastudios:cameraview:2.7.2")
    val room_version = "2.3.0"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")
//    // To use Kotlin Symbol Processing (KSP)
//    ksp("androidx.room:room-compiler:$room_version")
//
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0")



}



