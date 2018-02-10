import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.setValue

/*
 * Copyright (C) 2015 Antonio Leiva
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(Config.Android.compileSdkVersion)
    buildToolsVersion(Config.Android.buildToolsVersion)

    defaultConfig {
        applicationId = Config.Android.applicationId
        minSdkVersion(Config.Android.minSdkVersion)
        targetSdkVersion(Config.Android.targetSdkVersion)
        versionCode = Config.Android.versionCode
        versionName = Config.Android.versionName

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles("proguard-rules.pro")
        }
    }
}

dependencies {
    compile(Config.Libs.kotlin_std)
    compile(Config.Libs.appcompat)
    compile(Config.Libs.recyclerview)
    compile(Config.Libs.cardview)
    compile(Config.Libs.palette)
    compile(Config.Libs.design)
    compile(Config.Libs.eventbus)
    compile(Config.Libs.picasso)
    compile(Config.Libs.okhttp)
    compile(Config.Libs.okhttp_interceptor)
    compile(Config.Libs.retrofit)
    compile(Config.Libs.retrofit_gson)
    compile(Config.Libs.jobqueue)
    compile(Config.Libs.anko_sdk15)
    compile(Config.Libs.anko_support)
    compile(Config.Libs.anko_appcompat)
    compile(Config.Libs.anko_design)
    compile(Config.Libs.anko_cardview)
    compile(Config.Libs.anko_recyclerview)
    kapt(Config.Libs.dagger_compiler)
    compile(Config.Libs.dagger)

    testCompile(Config.TestLibs.junit)
    testCompile(Config.TestLibs.mockito)

    androidTestCompile(Config.TestLibs.mockito)
    androidTestCompile(Config.TestLibs.dexmaker)
    androidTestCompile(Config.TestLibs.dexmaker_mockito)
    androidTestCompile(Config.TestLibs.annotations)
    androidTestCompile(Config.TestLibs.espresso)
}