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
package org.sopt.stamp.feature.mission.model

import com.ramcosta.composedestinations.navargs.DestinationsNavTypeSerializer
import com.ramcosta.composedestinations.navargs.NavTypeSerializer
import org.sopt.stamp.domain.MissionLevel

data class MissionNavArgs(
    val id: Int,
    val title: String,
    val level: MissionLevel,
    val isCompleted: Boolean
)

fun MissionUiModel.toArgs() = MissionNavArgs(
    id = this.id,
    title = this.title,
    level = this.level,
    isCompleted = this.isCompleted
)

@NavTypeSerializer
class MissionNavArgsSerializer : DestinationsNavTypeSerializer<MissionNavArgs> {
    override fun toRouteString(value: MissionNavArgs): String {
        return "${value.id}::${value.title}::${value.level}::${value.isCompleted}"
    }

    override fun fromRouteString(routeStr: String): MissionNavArgs {
        val (id, title, level, isCompleted) = routeStr.split("::")
        return MissionNavArgs(
            id = id.toInt(),
            title = title,
            level = MissionLevel.of(level.toInt()),
            isCompleted = isCompleted.toBoolean()
        )
    }
}
