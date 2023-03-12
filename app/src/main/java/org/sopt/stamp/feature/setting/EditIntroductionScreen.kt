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
package org.sopt.stamp.feature.setting

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.EmptyResultBackNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import org.sopt.stamp.config.navigation.SettingNavGraph
import org.sopt.stamp.designsystem.component.layout.SoptColumn
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.util.DefaultPreview

@SettingNavGraph
@Destination("introduction")
@Composable
fun EditIntroductionScreen(
    resultNavigator: ResultBackNavigator<Boolean>,
    viewModel: EditIntroductionViewModel = hiltViewModel()
) {
    SoptTheme {
        SoptColumn {

        }
    }
}

@DefaultPreview
@Composable
private fun EditIntroductionScreenPreview() {
    EditIntroductionScreen(
        resultNavigator = EmptyResultBackNavigator(),
        viewModel = EditIntroductionViewModel()
    )
}
