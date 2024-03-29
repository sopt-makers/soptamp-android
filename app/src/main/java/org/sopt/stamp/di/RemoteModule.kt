/*
 * Copyright 2023 SOPT - Shout Our Passion Together
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sopt.stamp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.stamp.data.remote.api.RankService
import org.sopt.stamp.data.remote.api.SoptampService
import org.sopt.stamp.data.remote.api.StampService
import org.sopt.stamp.data.remote.api.UserService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RemoteModule {
    @Provides
    @Singleton
    fun provideStampService(
        retrofit: Retrofit
    ): StampService = retrofit.create(StampService::class.java)

    @Provides
    @Singleton
    fun provideSoptampService(
        retrofit: Retrofit
    ): SoptampService = retrofit.create(SoptampService::class.java)

    @Provides
    @Singleton
    fun provideRankingService(
        retrofit: Retrofit
    ): RankService = retrofit.create(RankService::class.java)

    @Provides
    @Singleton
    fun provideUserService(
        retrofit: Retrofit
    ): UserService = retrofit.create(UserService::class.java)
}
