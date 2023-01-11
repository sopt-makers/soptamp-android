package org.sopt.stamp.data.repository

import org.sopt.stamp.data.error.ErrorData
import org.sopt.stamp.data.mapper.toDomain
import org.sopt.stamp.data.remote.model.response.UserResponse
import org.sopt.stamp.data.source.UserDataSource
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
    ): Result<UserResponse> {
        val result = remote.signup(nickname, email, password, osType, clientToken).mapCatching { it }
        val exception = result.exceptionOrNull()
        return if (exception is ErrorData) {
            Result.failure(exception.toDomain())
        } else {
            result
        }
    }

    override suspend fun checkNickname(nickname: String): Result<UserResponse> {
        val result = remote.checkNickname(nickname).mapCatching { it }
        val exception = result.exceptionOrNull()
        return if (exception is ErrorData) {
            Result.failure(exception.toDomain())
        } else {
            result
        }
    }

    override suspend fun checkEmail(email: String): Result<UserResponse> {
        val result = remote.checkEmail(email).mapCatching { it }
        val exception = result.exceptionOrNull()
        return if (exception is ErrorData) {
            Result.failure(exception.toDomain())
        } else {
            result
        }
    }

    override suspend fun login(email: String, password: String): Result<UserResponse> {
        val result = remote.login(email, password).mapCatching { it }
        val exception = result.exceptionOrNull()
        return if (exception is ErrorData) {
            Result.failure(exception.toDomain())
        } else {
            result
        }
    }
}
