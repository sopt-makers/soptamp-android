package org.sopt.stamp.feature.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.stamp.domain.repository.RankingRepository
import org.sopt.stamp.feature.ranking.model.toUiModel

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val rankingRepository: RankingRepository
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
            .onSuccess { ranking -> _state.value = RankingState.Success(ranking, 12) }
            .onFailure { throwable ->
                _state.value = RankingState.Failure
            }
    }
}
