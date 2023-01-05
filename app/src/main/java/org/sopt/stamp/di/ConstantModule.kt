package org.sopt.stamp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.stamp.BuildConfig
import org.sopt.stamp.di.constant.String
import org.sopt.stamp.di.constant.Constant
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConstantModule {
    @Provides
    @Singleton
    @String(Constant.SOPTAMP_API_KEY)
    fun provideBaseUrl() = BuildConfig.SOPTAMP_API_KEY
}
