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

import com.antonioleiva.bandhookkotlin.domain.interactor.GetArtistDetailInteractor
import com.antonioleiva.bandhookkotlin.domain.interactor.GetRecommendedArtistsInteractor
import com.antonioleiva.bandhookkotlin.util.DelegatesExt

public interface DomainModule : GetRecommendedArtistsInteractorProvider

public interface GetRecommendedArtistsInteractorProvider {
    val recommendedArtistsInteractorProvider: GetRecommendedArtistsInteractor
    val artistDetailInteractorProvider: GetArtistDetailInteractor
}

class DomainModuleImpl(repositoryModule: RepositoryModule) : DomainModule,
        ArtistRepositorySingleton by repositoryModule {

    override val recommendedArtistsInteractorProvider by DelegatesExt.provider {
        GetRecommendedArtistsInteractor(artistRepository)
    }

    override val artistDetailInteractorProvider by DelegatesExt.provider {
        GetArtistDetailInteractor(artistRepository)
    }
}