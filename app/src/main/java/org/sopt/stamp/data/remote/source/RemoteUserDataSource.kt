/*
 * Copyright 2023 SOPT - Shout Our Passion Together
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sopt.stamp.data.remote.source

import org.sopt.stamp.data.remote.api.UserService
import org.sopt.stamp.data.remote.model.request.LoginRequest
import org.sopt.stamp.data.remote.model.request.SignUpRequest
import org.sopt.stamp.data.remote.model.request.UpdateNicknameRequest
import org.sopt.stamp.data.remote.model.request.UpdatePasswordRequest
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
        val response = userService.signup(
            SignUpRequest(
                nickname,
                email,
                password
            )
        )
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

    override suspend fun withdraw(userId: Int) {
        userService.withdraw(userId)
    }

    override suspend fun updatePassword(userId: Int, new: String) {
        userService.updatePassword(userId, UpdatePasswordRequest(new))
    }

    override suspend fun updateNickname(userId: Int, new: String) {
        userService.updateNickname(userId, UpdateNicknameRequest(new))
    }
}
