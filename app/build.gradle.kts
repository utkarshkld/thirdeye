plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs")
    id("de.undercouch.download")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")

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
    externalNativeBuild {
        cmake {
            version = "3.10.2"
            path = file("src/main/jni/CMakeLists.txt")
        }
    }

        packagingOptions {
            pickFirst ("META-INF/DEPENDENCIES")
            pickFirst ("META-INF/DEPENDENCIES")
        }

}
//project.extra["ASSET_DIR"] = projectDir.toString() + "/src/main/assets"

dependencies {

    implementation(kotlin("stdlib"))
    implementation("androidx.work:work-runtime:2.7.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.20")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.intuit.sdp:sdp-android:1.1.0") // To make the app responsive for various devices
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.localbroadcastmanager:localbroadcastmanager:1.1.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    implementation("org.apache.httpcomponents:httpclient:4.5.14") // To make the fetch call
    val nav_version = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    val camerax_version = "1.3.1"
    implementation("androidx.camera:camera-core:$camerax_version")
    implementation("androidx.camera:camera-camera2:$camerax_version")
    implementation("androidx.camera:camera-lifecycle:$camerax_version")
    implementation("androidx.camera:camera-view:$camerax_version")
    implementation("androidx.window:window:1.3.0-alpha02")
    testImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation("androidx.test:rules:1.5.0")
    testImplementation("androidx.test:runner:1.5.2")
    testImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("org.robolectric:robolectric:4.4")
    implementation("com.google.mediapipe:tasks-vision:0.20230731")
    implementation("com.google.mlkit:translate:17.0.2")
    implementation("com.google.mlkit:language-id:17.0.5")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.1.0")
    implementation("org.tensorflow:tensorflow-lite-gpu:2.3.0")
    implementation("com.google.mlkit:image-labeling:17.0.8")
    testImplementation("junit:junit:4.13.2")
    implementation("com.google.android.gms:play-services-tasks:18.1.0")
    implementation ("com.android.support:support-v4:28.0.0")

    implementation ("com.google.mlkit:text-recognition:16.0.0")

    // To recognize Chinese script
    implementation ("com.google.mlkit:text-recognition-chinese:16.0.0")

    // To recognize Devanagari script
    implementation ("com.google.mlkit:text-recognition-devanagari:16.0.0")

    // To recognize Japanese script
    implementation ("com.google.mlkit:text-recognition-japanese:16.0.0")

    // To recognize Korean script
    implementation ("com.google.mlkit:text-recognition-korean:16.0.0")

    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation ("com.google.android.gms:play-services-auth:20.2.0")
    implementation ("com.google.android.gms:play-services-identity:17.0.0")
    implementation ("com.google.android.gms:play-services-auth-api-phone:17.4.0")
    implementation ("com.google.android.gms:play-services-base:17.3.0")
    implementation ("com.google.android.play:app-update:2.1.0")
    implementation ("com.google.firebase:firebase-crashlytics:18.2.6")




}



