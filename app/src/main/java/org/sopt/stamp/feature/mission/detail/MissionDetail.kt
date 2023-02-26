package org.sopt.stamp.feature.mission.detail

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.EmptyResultBackNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import org.sopt.stamp.R
import org.sopt.stamp.config.navigation.MissionNavGraph
import org.sopt.stamp.designsystem.component.layout.SoptColumn
import org.sopt.stamp.designsystem.component.ratingbar.RatingBar
import org.sopt.stamp.designsystem.component.toolbar.Toolbar
import org.sopt.stamp.designsystem.component.toolbar.ToolbarIconType
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.domain.MissionLevel
import org.sopt.stamp.feature.mission.model.ImageModel
import org.sopt.stamp.feature.mission.model.MissionNavArgs
import org.sopt.stamp.feature.ranking.getRankTextColor
import org.sopt.stamp.util.DefaultPreview

@Composable
private fun HeaderView(
    title: String,
    stars: Int
) {
    Surface(
        modifier = Modifier
            .background(
                color = SoptTheme.colors.onSurface5,
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier.background(SoptTheme.colors.onSurface5),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RatingBar(
                icon = R.drawable.ic_star,
                stars = stars,
                selectedColor = getRankTextColor(rank = stars)
            )
            Text(
                text = title,
                style = SoptTheme.typography.sub1,
                color = SoptTheme.colors.onSurface90
            )
        }
    }
}

@Composable
private fun ImageContent(
    imageModel: ImageModel,
    onChangeImage: (images: ImageModel) -> Unit
) {
    val isImageEmpty = remember(imageModel) { imageModel.isEmpty() }
    val photoPickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) {
        onChangeImage(ImageModel.Local(it))
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(
                color = SoptTheme.colors.onSurface5,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        if (isImageEmpty) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = SoptTheme.colors.onSurface5,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "달성 사진을 올려주세요",
                    style = SoptTheme.typography.sub2,
                    color = SoptTheme.colors.onSurface50
                )
            }
        } else {
            when (imageModel) {
                is ImageModel.Local -> {
                    AsyncImage(
                        model = imageModel.uri[0],
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                is ImageModel.Remote -> {
                    AsyncImage(
                        model = imageModel.url[0],
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                else -> throw IllegalStateException("예외처리 했으므로 여긴 안 통과함")
            }
        }
    }
}

@Composable
private fun Memo(
    value: String,
    onValueChange: (String) -> Unit,
    borderColor: Color
) {
    val isEmpty = remember(value) { value.isEmpty() }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
            .defaultMinSize(minHeight = 132.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = if (isEmpty) 0.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp)
            ),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = if (isEmpty) SoptTheme.colors.onSurface5 else Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            textColor = SoptTheme.colors.onSurface90,
            placeholderColor = SoptTheme.colors.onSurface60
        ),
        textStyle = SoptTheme.typography.caption1,
        placeholder = {
            Text(
                text = "메모를 작성해 주세요.",
                style = SoptTheme.typography.caption1
            )
        }
    )
}

@MissionNavGraph
@Destination("detail")
@Composable
fun MissionDetailScreen(
    args: MissionNavArgs,
    resultNavigator: ResultBackNavigator<Boolean>,
    viewModel: MissionDetailViewModel = hiltViewModel()
) {
    val (id, title, level, isCompleted) = args
    val content by viewModel.content.collectAsState("")
    val imageModel by viewModel.imageModel.collectAsState(ImageModel.Empty)
    val isSuccess by viewModel.isSuccess.collectAsState(false)
    val isSubmitEnabled by viewModel.isSubmitEnabled.collectAsState(false)

    LaunchedEffect(id) {
        viewModel.initMissionState(id)
    }
    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            resultNavigator.navigateBack(true)
        }
    }

    SoptTheme {
        SoptColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            Toolbar(
                modifier = Modifier.padding(bottom = 10.dp),
                title = {
                    Text(
                        text = "미션",
                        style = SoptTheme.typography.h2,
                        modifier = Modifier.padding(start = 2.dp),
                        color = SoptTheme.colors.onSurface
                    )
                },
                iconOption = if (isCompleted) ToolbarIconType.NONE else ToolbarIconType.WRITE,
                onBack = {
                    resultNavigator.navigateBack()
                }
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                HeaderView(
                    title = title,
                    stars = level.value
                )
                Spacer(modifier = Modifier.height(12.dp))
                ImageContent(imageModel, viewModel::onChangeImage)
                Spacer(modifier = Modifier.height(12.dp))
                Memo(content, viewModel::onChangeContent, SoptTheme.colors.mint300)
            }

            Button(
                onClick = viewModel::onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                enabled = isSubmitEnabled,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = getRankTextColor(rank = level.value),
                    disabledBackgroundColor = getRankTextColor(rank = level.value).copy(alpha = 0.8f)
                ),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                Text(
                    text = "미션 완료",
                    style = SoptTheme.typography.h2,
                    color = if (level.value == 3) SoptTheme.colors.onSurface70 else Color.White
                )
            }
        }
    }
}

@DefaultPreview
@Composable
fun MissionDetailPreview() {
    val args = MissionNavArgs(
        id = 1,
        title = "앱잼 팀원 다 함께 바다 보고 오기",
        level = MissionLevel.of(2),
        isCompleted = false
    )
    SoptTheme {
        MissionDetailScreen(args, EmptyResultBackNavigator(), viewModel())
    }
}