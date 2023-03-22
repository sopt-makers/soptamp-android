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
package org.sopt.stamp.util.validation

data class Password(
    val value: String
) : Verifiable() {
    val isValid: Boolean = valid(PASSWORD_REGEX.toRegex(), value)
    val errorMessage: String = if (isValid) "" else INVALID_PASSWORD_MESSAGE

    companion object {
        @JvmStatic
        val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#\$%^&*]).{8,16}\$"
        const val INVALID_PASSWORD_MESSAGE = "비밀번호는 영문, 숫자, 특수문자 포함 8-16자여야 합니다."
    }
}

fun String.toPassword() = Password(this)
