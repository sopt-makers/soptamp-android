package org.sopt.stamp.feature.mission.detail.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.feature.mission.model.ImageModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageContent(
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