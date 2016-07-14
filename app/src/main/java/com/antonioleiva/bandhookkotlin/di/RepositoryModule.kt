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

package com.antonioleiva.bandhookkotlin.di

import com.antonioleiva.bandhookkotlin.data.CloudAlbumDataSet
import com.antonioleiva.bandhookkotlin.data.CloudArtistDataSet
import com.antonioleiva.bandhookkotlin.domain.repository.AlbumRepository
import com.antonioleiva.bandhookkotlin.domain.repository.ArtistRepository
import com.antonioleiva.bandhookkotlin.repository.AlbumRepositoryImpl
import com.antonioleiva.bandhookkotlin.repository.ArtistRepositoryImp

interface RepositoryModule : ArtistRepositorySingleton, AlbumRepositorySingleton

interface ArtistRepositorySingleton {
    val artistRepository: ArtistRepository
}

interface AlbumRepositorySingleton {
    val albumRepository: AlbumRepository
}

class RepositoryModuleImpl(appModule: AppModule, dataModule: DataModule) :
        RepositoryModule,
        LanguageSingleton by appModule, LastFmServiceSingleton by dataModule {

    override val artistRepository = ArtistRepositoryImp(listOf(CloudArtistDataSet(language, lastFmService)))
    override val albumRepository = AlbumRepositoryImpl(listOf(CloudAlbumDataSet(lastFmService)))
}