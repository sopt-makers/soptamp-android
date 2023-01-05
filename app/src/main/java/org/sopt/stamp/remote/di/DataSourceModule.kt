package org.sopt.stamp.remote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.stamp.data.source.MissionsDataSource
import org.sopt.stamp.remote.source.RemoteMissionsDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindRemoteMissionsDataSource(
        source: RemoteMissionsDataSource
    ): MissionsDataSource
}
