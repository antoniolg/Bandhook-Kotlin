# Bandhook Kotlin

This project is a small replica of the app I developed some time ago. [Bandhook can still be found on Play Store](https://play.google.com/store/apps/details?id=com.limecreativelabs.bandhook)

At the moment it will only connect to LastFm and retrieve some music bands. It will let navigate to the artist detail. This is what you´ll get:

<p align="center">
  <img src="art/bandhook.gif" alt="Bandhook Kotlin" />
</p>

The purpose of the project is to show a complex (at least in architecture) project entirely written using Kotlin. This example also supports the articles
written in [antonioleiva.com](http://antonioleiva.com) related to Kotlin for Android:

* [Kotlin for Android (I): Introduction](http://antonioleiva.com/kotlin-for-android-introduction/)
* [Kotlin for Android (II): Create a new project](http://antonioleiva.com/kotlin-android-create-project/)
* [Kotlin for Android (III): Extension functions and default values](http://antonioleiva.com/kotlin-android-extension-functions/)
* [Kotlin for Android (IV): Custom Views and Android Extensions](http://antonioleiva.com/kotlin-android-custom-views/)


This sample project is still in development, and could easily break or have some messy code. However, any idea or suggestion to improve it will be welcomed. Feel free to [open an issue](https://github.com/antoniolg/Bandhook-Kotlin/issues/new) if you think something could be improved.

## How to use this project

You can use Android Studio or Intellij to work with this repository.

First thing you will need to compile this project is to get an [API Key from Last.fm](http://www.lastfm.es/api). It will we used to connect to the service that will provide artists info. Then create a resource file `app/src/main/res/values/api_key.xml` (this path is ignored by git) with the following content:

```xml
<string name="last_fm_api_key">YOUR_KEY</string>
```

The `Kotlin` plugin for Android Studio is also required.

Many ideas on how this project is organized were taken from [Clean Contacts](https://github.com/PaNaVTEC/Clean-Contacts/blob/master/Readme.md) project, by [PaNaVTEC](https://github.com/PaNaVTEC). There are some slight differences, but reading [How to start section](https://github.com/PaNaVTEC/Clean-Contacts/blob/master/Readme.md#how-to-start-with-this-repository) will be helpful.

# License

    Copyright 2015 Antonio Leiva

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
