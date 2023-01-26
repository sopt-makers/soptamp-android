package org.sopt.stamp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.stamp.data.remote.source.RemoteMissionsDataSource
import org.sopt.stamp.data.remote.source.RemoteUserDataSource
import org.sopt.stamp.data.source.MissionsDataSource
import org.sopt.stamp.data.source.UserDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindRemoteMissionsDataSource(
        source: RemoteMissionsDataSource
    ): MissionsDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteUserDataSource(
        source: RemoteUserDataSource
    ): UserDataSource
}
