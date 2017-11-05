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

val config = ProjectConfiguration()

android {
    compileSdkVersion(config.android.compileSdkVersion)
    buildToolsVersion(config.android.buildToolsVersion)

    defaultConfig {
        applicationId = config.android.applicationId
        minSdkVersion(config.android.minSdkVersion)
        targetSdkVersion(config.android.targetSdkVersion)
        versionCode = config.android.versionCode
        versionName = config.android.versionName

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
    compile(config.libs.kotlin_std)
    compile(config.libs.appcompat)
    compile(config.libs.recyclerview)
    compile(config.libs.cardview)
    compile(config.libs.palette)
    compile(config.libs.design)
    compile(config.libs.eventbus)
    compile(config.libs.picasso)
    compile(config.libs.okhttp)
    compile(config.libs.okhttp_interceptor)
    compile(config.libs.retrofit)
    compile(config.libs.retrofit_gson)
    compile(config.libs.jobqueue)
    compile(config.libs.anko_sdk15)
    compile(config.libs.anko_support)
    compile(config.libs.anko_appcompat)
    compile(config.libs.anko_design)
    compile(config.libs.anko_cardview)
    compile(config.libs.anko_recyclerview)
    kapt(config.libs.dagger_compiler)
    compile(config.libs.dagger)

    testCompile(config.testLibs.junit)
    testCompile(config.testLibs.mockito)

    androidTestCompile(config.testLibs.mockito)
    androidTestCompile(config.testLibs.dexmaker)
    androidTestCompile(config.testLibs.dexmaker_mockito)
    androidTestCompile(config.testLibs.annotations)
    androidTestCompile(config.testLibs.espresso)
}