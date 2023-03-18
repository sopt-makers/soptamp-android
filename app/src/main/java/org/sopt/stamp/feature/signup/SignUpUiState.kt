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
package org.sopt.stamp.feature.signup

data class SignUpUiState(
    val nickname: String = "",
    val email: String = "",
    val isNewEmail: Boolean = false,
    val isNewNickname: Boolean = false,
    val password: String = "",
    val passwordCheck: String = "",
) {
    val isEmailEnabled: Boolean
        get() = EMAIL_PATTERN.matches(email) && email.isNotBlank()
    val isNicknameEnabled: Boolean
        get() = NICKNAME_PATTERN.matches(nickname) && nickname.isNotBlank()
    val isPasswordEnabled: Boolean
        get() = PASSWORD_PATTERN.matches(password) && password.isNotBlank()
    val isPasswordCheckEnabled: Boolean
        get() = password == passwordCheck && password.isNotBlank()

    private companion object {
        val EMAIL_PATTERN = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        val NICKNAME_PATTERN = Regex("[a-zA-Z가-힣]+")
        val PASSWORD_PATTERN = Regex("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}\$")
    }
}
