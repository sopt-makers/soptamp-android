package org.sopt.stamp.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.stamp.data.repository.RemoteMissionsRepository
import org.sopt.stamp.domain.repository.MissionsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindMissionsRepository(repository: RemoteMissionsRepository): MissionsRepository
}
