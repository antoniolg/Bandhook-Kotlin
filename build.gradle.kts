import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.setValue
import org.gradle.kotlin.dsl.repositories

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

buildscript {

    repositories {
        jcenter()
        google()
    }
    dependencies {
        classpath(Config.BuildPlugins.androidGradle)
        classpath(Config.BuildPlugins.kotlinGradlePlugin)
    }
}

allprojects {
    repositories {
        jcenter()
        google()
    }
}
