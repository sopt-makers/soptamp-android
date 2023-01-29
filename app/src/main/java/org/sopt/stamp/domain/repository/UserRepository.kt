package org.sopt.stamp.domain.repository

import org.sopt.stamp.domain.model.User

interface UserRepository {
    suspend fun signup(nickname: String, email: String, password: String, osType: String, clientToken: String): User
    suspend fun checkNickname(nickname: String): User
    suspend fun checkEmail(email: String): User
    suspend fun login(email: String, password: String): User
}