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

data class Nickname(
    val value: String
) : Verifiable() {
    val isValid: Boolean = valid(NICKNAME_REGEX.toRegex(), value)
    val errorMessage: String = if (isValid) "" else INVALID_NICKNAME_MESSAGE

    companion object {
        @JvmStatic
        val NICKNAME_REGEX = "^[A-Za-z가-힣]{0,11}$"
        const val INVALID_NICKNAME_MESSAGE = "한글/영문 10자 이하로 입력해주세요."
    }
}

fun String.toNickName() = Nickname(this)
