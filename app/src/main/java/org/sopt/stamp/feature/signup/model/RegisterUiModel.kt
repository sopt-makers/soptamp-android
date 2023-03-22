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
package org.sopt.stamp.feature.signup.model

import org.sopt.stamp.util.validation.toEmail
import org.sopt.stamp.util.validation.toNickName
import org.sopt.stamp.util.validation.toPassword

data class RegisterUiModel(
    val nickname: String = "",
    val nicknameCheckState: CheckState = CheckState.NONE,
    val nicknameCheckMessage: String = "",
    val email: String = "",
    val emailCheckState: CheckState = CheckState.NONE,
    val emailCheckMessage: String = "",
    val password: String = "",
    val passwordCheckState: CheckState = CheckState.NONE,
    val passwordConfirm: String = "",
    val passwordConfirmCheckState: CheckState = CheckState.NONE,
    val passwordCheckMessage: String = "",
    val errorMessage: String? = null,
    val isAllInputNotEmpty: Boolean = false,
    val isComplete: Boolean = false,
    val isCheckNickname: Boolean = false,
    val isCheckEmail: Boolean = false
) {

    fun updateNickname(value: String): RegisterUiModel {
        val nickname = value.toNickName()
        return copy(
            nickname = nickname.value,
            nicknameCheckState = if (value.isEmpty() or nickname.isValid) CheckState.NONE else CheckState.NONE_PASS,
            nicknameCheckMessage = nickname.errorMessage,
            isAllInputNotEmpty = isAllInputIsNotEmpty()
        )
    }

    fun updateEmail(value: String): RegisterUiModel {
        val email = value.toEmail()
        return copy(
            email = email.value,
            emailCheckState = if (value.isEmpty() or email.isValid) CheckState.NONE else CheckState.NONE_PASS,
            emailCheckMessage = email.errorMessage,
            isAllInputNotEmpty = isAllInputIsNotEmpty()
        )
    }

    fun updatePassword(value: String): RegisterUiModel {
        val password = value.toPassword()
        return copy(
            password = password.value,
            passwordCheckState = if (value.isEmpty() or password.isValid) CheckState.NONE else CheckState.NONE_PASS,
            passwordCheckMessage = password.errorMessage,
            isAllInputNotEmpty = isAllInputIsNotEmpty()
        )
    }

    fun updatePasswordConfirm(value: String): RegisterUiModel {
        val isEqualPassword = (value == password)
        return copy(
            passwordConfirm = value,
            passwordConfirmCheckState = if (value.isEmpty() or isEqualPassword) {
                CheckState.NONE
            } else {
                CheckState.NONE_PASS
            },
            passwordCheckMessage = if (isEqualPassword) {
                ""
            } else {
                NOT_EQUAL_PASSWORD_MESSAGE
            },
            isAllInputNotEmpty = isAllInputIsNotEmpty()
        )
    }

    private fun isAllInputIsNotEmpty() =
        (nickname.isNotEmpty() and email.isNotEmpty() and password.isNotEmpty() and passwordConfirm.isNotEmpty())

    companion object {
        val empty = RegisterUiModel()
        private const val NOT_EQUAL_PASSWORD_MESSAGE = "비밀번호가 일치하지 않습니다"
    }
}
