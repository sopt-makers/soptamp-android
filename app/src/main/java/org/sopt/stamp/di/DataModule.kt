package org.sopt.stamp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.stamp.data.repository.RemoteMissionsRepository
import org.sopt.stamp.data.repository.StampRepositoryImpl
import org.sopt.stamp.domain.repository.MissionsRepository
import org.sopt.stamp.domain.repository.StampRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindMissionsRepository(
        repository: RemoteMissionsRepository
    ): MissionsRepository

    @Binds
    @Singleton
    abstract fun bindStampRepository(
        repository: StampRepositoryImpl
    ): StampRepository
}
