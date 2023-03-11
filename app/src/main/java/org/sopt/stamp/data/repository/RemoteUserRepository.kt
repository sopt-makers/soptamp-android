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
