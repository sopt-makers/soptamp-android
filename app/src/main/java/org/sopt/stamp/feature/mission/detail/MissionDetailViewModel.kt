package org.sopt.stamp.feature.mission.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import org.sopt.stamp.designsystem.component.toolbar.ToolbarIconType
import org.sopt.stamp.domain.model.Archive
import org.sopt.stamp.domain.repository.StampRepository
import org.sopt.stamp.feature.mission.model.ImageModel
import timber.log.Timber
import javax.inject.Inject

data class PostUiState(
    val id: Int = -1,
    val imageUri: ImageModel = ImageModel.Empty,
    val content: String = "",
    val createdAt: String = "",
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: Throwable? = null,
    val isCompleted: Boolean = false,
    val toolbarIconType: ToolbarIconType = ToolbarIconType.NONE,
    val isDeleteSuccess: Boolean = false
) {
    companion object {
        fun from(data: Archive) = PostUiState(
            id = data.id,
            imageUri = if (data.images.isEmpty()) ImageModel.Empty else ImageModel.Remote(data.images),
            content = data.contents
        )
    }
}

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
    val imageModel = uiState.map { it.imageUri }
    val isSubmitEnabled = content.combine(imageModel) { content, image ->
        content.isNotEmpty() && !image.isEmpty()
    }
    val isCompleted = uiState.map { it.isCompleted }
    val toolbarIconType = uiState.map { it.toolbarIconType }
    val isEditable = toolbarIconType.map {
        it != ToolbarIconType.WRITE
    }
    val createdAt = uiState.map { it.createdAt }
        .filter { it.isNotEmpty() }

    fun initMissionState(id: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            uiState.update {
                it.copy(
                    id = id,
                    isError = false,
                    error = null,
                    isLoading = true,
                    isSuccess = false
                )
            }
            repository.getMissionContent(id)
                .onSuccess {
                    val result = PostUiState.from(it).copy(
                        id = id,
                        isCompleted = isCompleted,
                        toolbarIconType = if (isCompleted) ToolbarIconType.WRITE else ToolbarIconType.NONE,
                        createdAt = it.createdAt ?: ""
                    )
                    uiState.update { result }
                }.onFailure { error ->
                    Timber.e(error)
                    uiState.update {
                        it.copy(isLoading = false, isError = true, error = error)
                    }
                }
        }
    }

    fun onChangeContent(content: String) {
        uiState.update {
            it.copy(content = content)
        }
    }

    private fun onChangeToolbarState(toolbarIconType: ToolbarIconType) {
        uiState.update {
            it.copy(toolbarIconType = toolbarIconType)
        }
    }

    fun onPressToolbarIcon() {
        when (uiState.value.toolbarIconType) {
            ToolbarIconType.WRITE -> {
                onChangeToolbarState(ToolbarIconType.DELETE)
            }

            ToolbarIconType.DELETE -> {
                onDelete()
            }

            ToolbarIconType.NONE -> {}
        }
    }

    fun onChangeImage(imageModel: ImageModel) {
        uiState.update {
            it.copy(imageUri = imageModel)
        }
    }

    fun onSubmit() {
        viewModelScope.launch {
            val currentState = uiState.value
            val (id, imageUri, content) = currentState
            uiState.update {
                it.copy(isError = false, error = null, isLoading = true)
            }
            if (uiState.value.isCompleted) {
                repository.modifyMission(
                    missionId = id,
                    content = content,
                    imageUri = imageUri
                ).onSuccess {
                    uiState.update {
                        it.copy(isLoading = false, isSuccess = true)
                    }
                }.onFailure { error ->
                    Timber.e(error)
                    uiState.update {
                        it.copy(isLoading = false, isError = true, error = error, isSuccess = false)
                    }
                }
            } else {
                repository.completeMission(
                    missionId = id,
                    content = content,
                    imageUri = imageUri
                ).onSuccess {
                    uiState.update {
                        it.copy(isLoading = false, isSuccess = true)
                    }
                }.onFailure { error ->
                    Timber.e(error)
                    uiState.update {
                        it.copy(isLoading = false, isError = true, error = error, isSuccess = false)
                    }
                }
            }
        }
    }

    private fun onDelete() {
        viewModelScope.launch {
            val currentState = uiState.value
            val (id) = currentState
            uiState.update {
                it.copy(isError = false, error = null, isLoading = true)
            }
            repository.deleteMission(id)
                .onSuccess {
                    uiState.update {
                        it.copy(isLoading = false, isDeleteSuccess = true)
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