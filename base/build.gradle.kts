plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.compose.compiler) apply false
    kotlin("plugin.serialization") version "2.0.0"
}

android {

    namespace = "com.zzy.base"
    compileSdk = 34

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    packaging{
        resources{
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/INDEX.LIST"
            excludes += "/META-INF/io.netty.versions.properties"
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Multiplatform
    // Navigator
    api(libs.voyager.navigator)
    // Screen Model
    api(libs.voyager.screenmodel)
    // BottomSheetNavigator
    api(libs.voyager.bottom.sheet.navigator)
    // TabNavigator
    api(libs.voyager.tab.navigator)
    // Transitions
    api(libs.voyager.transitions)

    //kotlin
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // ktor
    // HTTP引擎: 处理网络请求.
//    api (libs.ktor.server.core)
//    api (libs.ktor.server.netty)
//    api (libs.ktor.client.android)
// 用于设置JSON序列化和逆序列化, 向多平台项目推荐.
//    api (libs.ktor.client.serialization)
// kotlinx.serialization, 用于实体序列化
    api (libs.kotlinx.serialization.json)
// 用于打印HTTP请求
//    api(libs.ktor.client.logging.jvm)
//    api(libs.ktor.server.websockets)

    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.activity.compose)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}