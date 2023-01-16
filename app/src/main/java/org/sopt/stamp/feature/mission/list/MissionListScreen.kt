package org.sopt.stamp.feature.mission.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import org.sopt.stamp.R
import org.sopt.stamp.config.navigation.MissionNavGraph
import org.sopt.stamp.designsystem.component.button.SoptampFloatingButton
import org.sopt.stamp.designsystem.component.button.SoptampIconButton
import org.sopt.stamp.designsystem.component.dialog.NetworkErrorDialog
import org.sopt.stamp.designsystem.component.layout.LoadingScreen
import org.sopt.stamp.designsystem.component.mission.MissionComponent
import org.sopt.stamp.designsystem.component.topappbar.SoptTopAppBar
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.domain.MissionLevel
import org.sopt.stamp.domain.model.MissionsFilter
import org.sopt.stamp.feature.destinations.MissionDetailScreenDestination
import org.sopt.stamp.feature.mission.MissionsState
import org.sopt.stamp.feature.mission.MissionsViewModel
import org.sopt.stamp.feature.mission.model.MissionListUiModel
import org.sopt.stamp.feature.mission.model.MissionNavArgs
import org.sopt.stamp.feature.mission.model.MissionUiModel
import org.sopt.stamp.feature.mission.model.toArgs

@MissionNavGraph(true)
@Destination("list")
@Composable
fun MissionListScreen(
    missionsViewModel: MissionsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<MissionDetailScreenDestination, Boolean>
) {
    val state by missionsViewModel.state.collectAsState()

    resultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> Unit
            is NavResult.Value -> {
                if (result.value) missionsViewModel.fetchMissions()
            }
        }
    }
    SoptTheme {
        when (state) {
            MissionsState.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }

            MissionsState.Failure -> NetworkErrorDialog {
                missionsViewModel.fetchMissions()
            }

            is MissionsState.Success -> MissionListScreen(
                missionListUiModel = (state as MissionsState.Success).missionListUiModel,
                menuTexts = MissionsFilter.getTitleOfMissionsList(),
                onMenuClick = { filter -> missionsViewModel.fetchMissions(filter) },
                onMissionItemClick = { item -> navigator.navigate(MissionDetailScreenDestination(item)) }
            )
        }
    }
}

@Composable
fun MissionListScreen(
    missionListUiModel: MissionListUiModel,
    menuTexts: List<String>,
    onMenuClick: (String) -> Unit = {},
    onMissionItemClick: (item: MissionNavArgs) -> Unit = {}
) {
    Scaffold(
        topBar = {
            MissionListHeader(
                title = missionListUiModel.title,
                menuTexts = menuTexts,
                onMenuClick = { onMenuClick(it) }
            )
        },
        floatingActionButton = { SoptampFloatingButton("랭킹 보기") },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 0.dp,
                    bottom = paddingValues.calculateBottomPadding(),
                    start = 8.dp,
                    end = 8.dp
                )
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(missionListUiModel.missionList) { missionUiModel ->
                    MissionComponent(
                        mission = missionUiModel,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 20.dp),
                        onClick = {
                            onMissionItemClick(missionUiModel.toArgs())
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MissionListHeader(
    title: String,
    menuTexts: List<String>,
    onMenuClick: (String) -> Unit = {}
) {
    SoptTopAppBar(
        title = { MissionListHeaderTitle(title = title) },
        dropDownButton = { DropDownMenuButton(menuTexts) }
    )
}

@Composable
fun MissionListHeaderTitle(
    title: String
) {
    Text(
        text = title,
        color = Color.Black,
        style = SoptTheme.typography.h2
    )
}

@Composable
fun DropDownMenuButton(
    menuTexts: List<String>,
    onMenuClick: (String) -> Unit = {}
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    Box {
        SoptampIconButton(
            imageVector = if (isMenuExpanded) {
                ImageVector.vectorResource(id = R.drawable.up_expand)
            } else {
                ImageVector.vectorResource(id = R.drawable.down_expand)
            }
        )
        DropdownMenu(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White
                )
                .padding(vertical = 12.dp),
            expanded = isMenuExpanded,
            offset = DpOffset((-69).dp, 12.dp),
            onDismissRequest = { isMenuExpanded = false }
        ) {
            menuTexts.forEach { menuText ->
                DropdownMenuItem(
                    contentPadding = PaddingValues(
                        horizontal = 20.dp,
                        vertical = 8.dp
                    ),
                    onClick = {
                        onMenuClick(menuText)
                        selectedIndex = menuTexts.indexOf(menuText)
                        isMenuExpanded = false
                    }
                ) {
                    Text(
                        text = menuText,
                        style = SoptTheme.typography.h3,
                        color = if (menuText == menuTexts[selectedIndex]) Color.Black else SoptTheme.colors.onSurface40
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMissionListScreen() {
    val missionListUiModel = MissionListUiModel(
        title = "완료 미션",
        missionList = listOf(
            MissionUiModel(
                id = 1,
                title = "세미나 끝나고 뒷풀이 1시까지 달리기",
                level = MissionLevel.of(3),
                isCompleted = true
            ),
            MissionUiModel(
                id = 2,
                title = "세미나 끝나고 뒷풀이 2시까지 달리기",
                level = MissionLevel.of(1),
                isCompleted = false
            ),
            MissionUiModel(
                id = 3,
                title = "세미나 끝나고 뒷풀이 3시까지 달리기",
                level = MissionLevel.of(2),
                isCompleted = false
            ),
            MissionUiModel(
                id = 4,
                title = "세미나 끝나고 뒷풀이 4시까지 달리기",
                level = MissionLevel.of(3),
                isCompleted = true
            ),
            MissionUiModel(
                id = 5,
                title = "세미나 끝나고 뒷풀이 5시까지 달리기",
                level = MissionLevel.of(1),
                isCompleted = false
            ),
            MissionUiModel(
                id = 6,
                title = "세미나 끝나고 뒷풀이 6시까지 달리기",
                level = MissionLevel.of(2),
                isCompleted = false
            ),
            MissionUiModel(
                id = 7,
                title = "세미나 끝나고 뒷풀이 7시까지 달리기",
                level = MissionLevel.of(3),
                isCompleted = false
            ),
            MissionUiModel(
                id = 8,
                title = "세미나 끝나고 뒷풀이 8시까지 달리기",
                level = MissionLevel.of(1),
                isCompleted = false
            ),
            MissionUiModel(
                id = 9,
                title = "세미나 끝나고 뒷풀이 9시까지 달리기",
                level = MissionLevel.of(2),
                isCompleted = true
            )
        )
    )
    SoptTheme {
        MissionListScreen(
            missionListUiModel,
            listOf("전체 미션", "완료 미션", "미완료 미션")
        )
    }
}
