package org.sopt.stamp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.stamp.data.repository.RemoteMissionsRepository
import org.sopt.stamp.data.repository.RemoteRankingRepository
import org.sopt.stamp.data.repository.RemoteUserRepository
import org.sopt.stamp.data.repository.StampRepositoryImpl
import org.sopt.stamp.domain.repository.MissionsRepository
import org.sopt.stamp.domain.repository.RankingRepository
import org.sopt.stamp.domain.repository.StampRepository
import org.sopt.stamp.domain.repository.UserRepository
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
    abstract fun bindRankRepository(
        repository: RemoteRankingRepository
    ): RankingRepository

    @Binds
    @Singleton
    abstract fun bindStampRepository(
        repository: StampRepositoryImpl
    ): StampRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        repository: RemoteUserRepository
    ): UserRepository
}
