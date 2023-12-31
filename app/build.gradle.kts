plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.tokolia"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tokolia"
        minSdk = 31
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

    packaging{
        resources{
            excludes += "com/itextpdf/io/font/cmap_info.txt"
            excludes += "com/itextpdf/io/font/cmap/*"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("com.google.android.material:material:1.11.0")
    //room version
    val room_version = "2.4.0"
    val lifecycle_version = "2.4.0"

    //room
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    //ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
    //LiveData
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")

    //Annotation processor
    annotationProcessor("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")


    //implement for pdf
    implementation("com.itextpdf:itext7-core:7.1.3")
    implementation("com.github.barteksc:android-pdf-viewer:2.8.2")
    //implementation("androidx.core:core:1.6.0")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}