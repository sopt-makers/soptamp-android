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
package org.sopt.stamp.feature.setting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class EditIntroductionUiState(
    val introduction: String = "",
    val isFocused: Boolean = false
)

@HiltViewModel
class EditIntroductionViewModel @Inject constructor() : ViewModel() {
    private val uiState = MutableStateFlow(EditIntroductionUiState())
    val isFocused = uiState.map { it.isFocused }
    val introduction = uiState.map { it.introduction }

    fun onUpdateFocusState(isFocused: Boolean) {
        uiState.update { it.copy(isFocused = isFocused) }
    }

    fun onIntroductionChanged(introduction: String) {
        uiState.value = uiState.value.copy(introduction = introduction)
    }
}
