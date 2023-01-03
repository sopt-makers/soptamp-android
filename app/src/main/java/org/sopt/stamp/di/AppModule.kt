package org.sopt.stamp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import org.sopt.stamp.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {
    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(): String = BuildConfig.SOPTAMP_API_KEY
}
