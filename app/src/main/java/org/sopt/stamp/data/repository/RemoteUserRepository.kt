package org.sopt.stamp.data.repository

import org.sopt.stamp.data.source.UserDataSource
import org.sopt.stamp.domain.model.User
import org.sopt.stamp.domain.repository.UserRepository
import javax.inject.Inject

class RemoteUserRepository @Inject constructor(
    private val remote: UserDataSource
) : UserRepository {
    override suspend fun signup(
        nickname: String,
        email: String,
        password: String,
        osType: String,
        clientToken: String
    ): User = remote.signup(nickname, email, password, osType, clientToken).toUser()

    override suspend fun checkNickname(nickname: String): User = remote.checkNickname(nickname).toUser()

    override suspend fun checkEmail(email: String): User = remote.checkEmail(email).toUser()

    override suspend fun login(email: String, password: String): User = remote.login(email, password).toUser()
}