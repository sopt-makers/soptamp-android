package org.sopt.stamp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.stamp.data.remote.service.StampService
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
}
