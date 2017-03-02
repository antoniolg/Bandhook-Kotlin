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

package com.antonioleiva.bandhookkotlin.domain.interactor.base

import android.content.Context
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.config.Configuration

class CustomJobManager(context: Context) : JobManager(CustomJobManager.getJobManagerConfiguration(context)) {

    companion object {

        private fun getJobManagerConfiguration(context: Context): Configuration {

            return Configuration.Builder(context)
                    .minConsumerCount(1)    // always keep at least one consumer alive
                    .maxConsumerCount(3)    // up to 3 consumers at NextOnEditorActionListener time
                    .loadFactor(3)          // 3 jobs per consumer
                    .consumerKeepAlive(120) // wait 2 minutes
                    .build()

        }
    }
}