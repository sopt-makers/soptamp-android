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
package org.sopt.stamp.feature.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.stamp.data.local.SoptampDataStore
import org.sopt.stamp.domain.repository.RankingRepository
import org.sopt.stamp.feature.ranking.model.RankingListUiModel
import org.sopt.stamp.feature.ranking.model.toUiModel
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val rankingRepository: RankingRepository,
    private val dataStore: SoptampDataStore
) : ViewModel() {
    private val _state: MutableStateFlow<RankingState> = MutableStateFlow(RankingState.Loading)
    val state: StateFlow<RankingState> = _state.asStateFlow()

    init {
        fetchRanking()
    }

    fun fetchRanking() = viewModelScope.launch {
        _state.value = RankingState.Loading
        rankingRepository.getRanking()
            .mapCatching { it.toUiModel() }
            .onSuccess { ranking -> onSuccessStateChange(ranking) }
            .onFailure {
                _state.value = RankingState.Failure
            }
    }

    private fun onSuccessStateChange(ranking: RankingListUiModel) {
        _state.value = RankingState.Success(ranking, dataStore.userId)
    }
}
