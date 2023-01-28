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