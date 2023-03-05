package org.sopt.stamp.feature.mission.detail

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.EmptyResultBackNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.coroutines.delay
import org.sopt.stamp.R
import org.sopt.stamp.config.navigation.MissionNavGraph
import org.sopt.stamp.designsystem.component.layout.SoptColumn
import org.sopt.stamp.designsystem.component.toolbar.Toolbar
import org.sopt.stamp.designsystem.component.toolbar.ToolbarIconType
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.domain.MissionLevel
import org.sopt.stamp.feature.mission.detail.component.Header
import org.sopt.stamp.feature.mission.detail.component.Memo
import org.sopt.stamp.feature.mission.model.ImageModel
import org.sopt.stamp.feature.mission.model.MissionNavArgs
import org.sopt.stamp.feature.ranking.getRankBackgroundColor
import org.sopt.stamp.feature.ranking.getRankTextColor
import org.sopt.stamp.util.DefaultPreview

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ImageContent(
    imageModel: ImageModel,
    onChangeImage: (images: ImageModel) -> Unit
) {
    val isImageEmpty = remember(imageModel) { imageModel.isEmpty() }
    val photoPickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) {
        onChangeImage(ImageModel.Local(it))
    }
    val pageCount = remember(imageModel) { imageModel.size }

    HorizontalPager(pageCount) { page ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(
                    color = SoptTheme.colors.onSurface5,
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
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
                if (isImageEmpty) {
                    Text(
                        text = "달성 사진을 올려주세요",
                        style = SoptTheme.typography.sub2,
                        color = SoptTheme.colors.onSurface50
                    )
                } else {
                    when (imageModel) {
                        is ImageModel.Local -> {
                            AsyncImage(
                                model = imageModel.uri[page],
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }

                        is ImageModel.Remote -> {
                            AsyncImage(
                                model = imageModel.url[page],
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
    }
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
    val lottieResId = remember(level) {
        when (level.value) {
            1 -> R.raw.purplestamp
            2 -> R.raw.pinkstamps
            else -> R.raw.greenstamp
        }
    }
    val lottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(lottieResId)
    )
    val progress by animateLottieCompositionAsState(
        composition = lottieComposition,
        isPlaying = isSuccess
    )

    LaunchedEffect(id) {
        viewModel.initMissionState(id)
    }
    LaunchedEffect(isSuccess, progress) {
        if (progress >= 0.99f && isSuccess) {
            delay(500L)
            resultNavigator.navigateBack(true)
        }
    }

    SoptTheme {
        SoptColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(SoptTheme.colors.white)
        ) {
            Toolbar(
                modifier = Modifier.padding(bottom = 10.dp),
                title = {
                    Text(
                        text = "미션",
                        style = SoptTheme.typography.h2,
                        modifier = Modifier.padding(start = 4.dp),
                        color = SoptTheme.colors.onSurface
                    )
                },
                iconOption = if (isCompleted) ToolbarIconType.WRITE else ToolbarIconType.NONE,
                onBack = {
                    resultNavigator.navigateBack()
                }
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Header(
                    title = title,
                    stars = level.value
                )
                Spacer(modifier = Modifier.height(12.dp))
                ImageContent(imageModel, viewModel::onChangeImage)
                Spacer(modifier = Modifier.height(12.dp))
                Memo(content, viewModel::onChangeContent, getRankTextColor(level.value))
            }

            Button(
                onClick = viewModel::onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                enabled = isSubmitEnabled,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = getRankTextColor(level.value),
                    disabledBackgroundColor = getRankBackgroundColor(level.value)
                ),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                Text(
                    text = "미션 완료",
                    style = SoptTheme.typography.h2,
                    color = if (level.value == 3) SoptTheme.colors.onSurface70 else SoptTheme.colors.white
                )
            }
        }
        if (isSuccess) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.55f)),
                contentAlignment = Alignment.Center
            ) {
                LottieAnimation(
                    composition = lottieComposition,
                    progress = { progress }
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