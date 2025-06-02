import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
}

fun getMapkitApiKey(): String {
    val properties = Properties()
    val stream = project.file("keystore.properties").inputStream()
    properties.load(stream)
    stream.close()
    return properties.getProperty("MAPKIT_API_KEY", "")
}

fun getGeocoderApiKey(): String {
    val properties = Properties()
    val stream = project.file("keystore.properties").inputStream()
    properties.load(stream)
    stream.close()
    return properties.getProperty("GEOCODER_API_KEY", "")
}

fun getOpenWeatherApiKey(): String {
    val properties = Properties()
    val stream = project.file("keystore.properties").inputStream()
    properties.load(stream)
    stream.close()
    return properties.getProperty("OPEN_WEATHER_API_KEY", "")
}

android {
    namespace = "com.dmitrystonie.sunrisemoonriseapp"
    compileSdk = 35

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.dmitrystonie.sunrisemoonriseapp"
        minSdk = 26
        targetSdk = 35
        versionCode = 4
        versionName = "1.0.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val mapkitApiKey = getMapkitApiKey()
        val geocoderApiKey = getGeocoderApiKey()
        val openWeatherApiKey = getOpenWeatherApiKey()

        buildConfigField("String", "MAPKIT_API_KEY", "\"${mapkitApiKey}\"")
        buildConfigField("String", "GEOCODER_API_KEY", "\"${geocoderApiKey}\"")
        buildConfigField("String", "OPEN_WEATHER_API_KEY", "\"${openWeatherApiKey}\"")
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)

    //hilt
    val hilt_version = "2.56.1"
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
    //retrofit
    val okHttp_version = "4.8.0"
    val retrofit_version = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.squareup.retrofit2:adapter-rxjava3:$retrofit_version")
    implementation("com.squareup.okhttp3:okhttp:$okHttp_version")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttp_version")
    //liveData
    val lifecycle_version = "2.8.7"
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    //material
    val material_version = "1.12.0"
    implementation("com.google.android.material:material:$material_version")
    //room
    val room_version = "2.7.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    //MapKit
    val mapkit_version = "4.15.0-lite"
    implementation("com.yandex.android:maps.mobile:$mapkit_version")
    //fragment
    val fragment_version = "1.8.6"
    implementation("androidx.fragment:fragment-ktx:$fragment_version")
    //lifecycle ktx
    val lifecycle_ktx_version = "2.8.7"
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_ktx_version")
    //espresso
    val runner_version = "1.6.1"
    val core_version = "1.6.1"
    val espresso_version = "3.6.1"
    val ext_junit_version = "1.2.1"
    val hamcrest_version = "2.2"
    androidTestImplementation("androidx.test:runner:$runner_version")
    androidTestImplementation("androidx.test:rules:$core_version")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espresso_version")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:$espresso_version")
    androidTestImplementation("androidx.test.ext:junit:$ext_junit_version")
    androidTestImplementation("org.hamcrest:hamcrest:$hamcrest_version")
    debugImplementation("androidx.test:core-ktx:$core_version")
    implementation("androidx.test.espresso:espresso-idling-resource:$espresso_version")

}

kapt {
    correctErrorTypes = true
}