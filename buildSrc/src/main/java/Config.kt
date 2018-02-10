private const val kotlinVersion = "1.2.21"
private const val androidGradleVersion = "3.0.1"

// Compile dependencies
private const val supportVersion = "27.0.2"
private const val ankoVersion = "0.10.4"
private const val daggerVersion = "2.14.1"
private const val retrofitVersion = "2.3.0"
private const val okhttpVersion = "3.9.1"
private const val eventBusVersion = "2.4.1"
private const val picassoVersion = "2.5.2"
private const val priorityJobQueueVersion = "2.0.1"

// Unit tests
private const val mockitoVersion = "2.13.0"

object Config {

    object BuildPlugins {
        val androidGradle = "com.android.tools.build:gradle:$androidGradleVersion"
        val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    }

    object Android {
        val buildToolsVersion = "27.0.3"
        val minSdkVersion = 19
        val targetSdkVersion = 27
        val compileSdkVersion = 27
        val applicationId = "com.antonioleiva.bandhookkotlin"
        val versionCode = 1
        val versionName = "0.1"

    }

    object Libs {
        val kotlin_std = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
        val appcompat = "com.android.support:appcompat-v7:$supportVersion"
        val recyclerview = "com.android.support:recyclerview-v7:$supportVersion"
        val cardview = "com.android.support:cardview-v7:$supportVersion"
        val palette = "com.android.support:palette-v7:$supportVersion"
        val design = "com.android.support:design:$supportVersion"
        val eventbus = "de.greenrobot:eventbus:$eventBusVersion"
        val picasso = "com.squareup.picasso:picasso:$picassoVersion"
        val okhttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"
        val okhttp_interceptor = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
        val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        val retrofit_gson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        val jobqueue = "com.birbit:android-priority-jobqueue:$priorityJobQueueVersion"
        val anko_sdk15 = "org.jetbrains.anko:anko-sdk15:$ankoVersion"
        val anko_support = "org.jetbrains.anko:anko-support-v4:$ankoVersion"
        val anko_appcompat = "org.jetbrains.anko:anko-appcompat-v7:$ankoVersion"
        val anko_design = "org.jetbrains.anko:anko-design:$ankoVersion"
        val anko_cardview = "org.jetbrains.anko:anko-cardview-v7:$ankoVersion"
        val anko_recyclerview = "org.jetbrains.anko:anko-recyclerview-v7:$ankoVersion"
        val dagger_compiler = "com.google.dagger:dagger-compiler:$daggerVersion"
        val dagger = "com.google.dagger:dagger:$daggerVersion"
    }

    object TestLibs {
        val junit = "junit:junit:4.12"
        val mockito = "org.mockito:mockito-core:$mockitoVersion"
        val dexmaker = "com.google.dexmaker:dexmaker:1.2"
        val dexmaker_mockito = "com.google.dexmaker:dexmaker-mockito:1.2"
        val annotations = "com.android.support:support-annotations:$supportVersion"
        val espresso = "com.android.support.test.espresso:espresso-core:2.2.2"
    }
}