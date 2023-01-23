package org.sopt.stamp.data.source

import org.sopt.stamp.data.remote.model.response.UserResponse

interface UserDataSource {
    suspend fun signup(nickname: String, email: String, password: String, osType: String, clientToken: String): UserResponse
    suspend fun checkNickname(nickname: String): UserResponse
    suspend fun checkEmail(email: String): UserResponse
    suspend fun login(email: String, password: String): UserResponse
}
