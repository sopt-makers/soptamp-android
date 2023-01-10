package org.sopt.stamp.feature.mission.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import org.sopt.stamp.domain.repository.StampRepository
import org.sopt.stamp.feature.mission.model.ImageModel
import timber.log.Timber
import javax.inject.Inject

data class PostUiState(
    val id: Int = -1,
    val imageUri: ImageModel = ImageModel.Empty,
    val content: String = "",
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: Throwable? = null,
)

@HiltViewModel
class MissionDetailViewModel @Inject constructor(
    private val repository: StampRepository
) : ViewModel() {
    private val uiState = MutableStateFlow(PostUiState())

    val isError = uiState.map { it.isError }
        .zip(uiState.map { it.error }) { isError, error ->
            Pair(isError, error)
        }
    val isSuccess = uiState.map { it.isSuccess }
    val content = uiState.map { it.content }

    fun onChangeContent(content: String) {
        uiState.update {
            it.copy(content = content)
        }
    }

    fun onChangeImage(imageModel: ImageModel) {
        uiState.update {
            it.copy(imageUri = imageModel)
        }
    }

    fun onUpdateId(id: Int) {
        uiState.update {
            it.copy(id = id)
        }
    }

    fun onSubmit() {
        viewModelScope.launch {
            val currentState = uiState.value
            val (id, imageUri, content) = currentState
            uiState.update {
                it.copy(isError = false, error = null, isLoading = true)
            }
            repository.completeMission(id, imageUri, content)
                .onSuccess {
                    uiState.update {
                        it.copy(isLoading = false, isSuccess = true)
                    }
                }.onFailure { error ->
                    Timber.e(error)
                    uiState.update {
                        it.copy(isLoading = false, isError = true, error = error)
                    }
                }
        }
    }
}
