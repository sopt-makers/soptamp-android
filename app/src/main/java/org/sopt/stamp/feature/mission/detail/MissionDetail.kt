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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
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
import org.sopt.stamp.feature.mission.model.MissionNavArgs
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
            RatingBar(icon = R.drawable.ic_star, stars = stars)
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
    content: Any? = null
) {
    val isImageEmpty = remember(content) { content == null }
    val photoPickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        // TODO by Nunu 이미지 피커 결과 업데이트 하기
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
            AsyncImage(
                model = content,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
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
    resultNavigator: ResultBackNavigator<Boolean>
) {
    val (id, title, level, isCompleted) = args
    LaunchedEffect(id) {
        // TODO by Nunu 여기에 서버통신 로직 넣기
    }

    var memo by remember { mutableStateOf("") }
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
                ImageContent(
                    "https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg"
                )
                Spacer(modifier = Modifier.height(12.dp))
                Memo(memo, { memo = it }, SoptTheme.colors.mint300)
            }

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp, top = 12.dp),
                enabled = false,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = SoptTheme.colors.mint300,
                    disabledBackgroundColor = SoptTheme.colors.mint300.copy(alpha = 0.8f)
                )
            ) {
                Text(
                    text = "제출",
                    style = SoptTheme.typography.caption1,
                    color = SoptTheme.colors.onSurface70
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
        isCompleted = false,
    )
    SoptTheme {
        MissionDetailScreen(args, EmptyResultBackNavigator())
    }
}
