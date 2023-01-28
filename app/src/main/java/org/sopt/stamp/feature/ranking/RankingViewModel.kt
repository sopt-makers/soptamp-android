package org.sopt.stamp.feature.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.stamp.data.local.SoptampDataStore
import org.sopt.stamp.domain.repository.RankingRepository
import org.sopt.stamp.feature.ranking.model.RankingListUiModel
import org.sopt.stamp.feature.ranking.model.toUiModel

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
            .onFailure { throwable ->
                _state.value = RankingState.Failure
            }
    }

    private suspend fun onSuccessStateChange(ranking: RankingListUiModel) {
        dataStore.userId.collect{userId ->
            _state.value = RankingState.Success(ranking, userId)
        }
    }

}
