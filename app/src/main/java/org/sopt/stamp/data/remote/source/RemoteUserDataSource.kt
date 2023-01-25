package org.sopt.stamp.data.remote.source

import org.sopt.stamp.data.remote.api.UserService
import org.sopt.stamp.data.remote.model.request.LoginRequest
import org.sopt.stamp.data.remote.model.response.UserResponse
import org.sopt.stamp.data.source.UserDataSource
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
    ): UserResponse {
        val response = userService.signup(nickname, email, password, osType, clientToken)
        return UserResponse(
            response.body()?.userId,
            response.body()?.message,
            response.code()
        )
    }

    override suspend fun checkNickname(nickname: String): UserResponse {
        val response = userService.checkNickname(nickname)
        return UserResponse(
            response.body()?.userId,
            response.body()?.message,
            response.code()
        )
    }

    override suspend fun checkEmail(email: String): UserResponse {
        val response = userService.checkEmail(email)
        return UserResponse(
            response.body()?.userId,
            response.body()?.message,
            response.code()
        )
    }

    override suspend fun login(email: String, password: String): UserResponse {
        val response = userService.login(
            LoginRequest(email, password)
        )
        return UserResponse(
            response.body()?.userId,
            response.body()?.message,
            response.code()
        )
    }
}
