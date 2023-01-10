package org.sopt.stamp.data.remote.source

import org.sopt.stamp.data.error.ErrorData
import org.sopt.stamp.data.remote.api.UserService
import org.sopt.stamp.data.remote.model.response.UserResponse
import org.sopt.stamp.data.source.UserDataSource
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val userService: UserService
) : UserDataSource {
    override suspend fun signup(
        nickname: String,
        email: String,
        password: String,
        osType: String,
        clientToken: String
    ): Result<UserResponse> {
        val result = kotlin.runCatching { userService.signup(nickname, email, password, osType, clientToken) }
        return when (val exception = result.exceptionOrNull()) {
            null -> result
            is UnknownHostException -> return Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }


    override suspend fun checkNickname(nickname: String): Result<UserResponse> {
        val result = kotlin.runCatching { userService.checkNickname(nickname) }
        return when (val exception = result.exceptionOrNull()) {
            null -> result
            is UnknownHostException -> return Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }

    override suspend fun checkEmail(email: String): Result<UserResponse> {
        val result = kotlin.runCatching { userService.checkEmail(email) }
        return when (val exception = result.exceptionOrNull()) {
            null -> result
            is UnknownHostException -> return Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }

    override suspend fun login(email: String, password: String): Result<UserResponse> {
        val result = kotlin.runCatching { userService.login(email, password) }
        return when (val exception = result.exceptionOrNull()) {
            null -> result
            is UnknownHostException -> return Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }
}