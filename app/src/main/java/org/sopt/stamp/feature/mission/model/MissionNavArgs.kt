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
    val isCompleted: Boolean,
    val isMe: Boolean,
    val userId: Int
)

fun MissionUiModel.toArgs(isMe: Boolean, userId: Int) = MissionNavArgs(
    id = this.id,
    title = this.title,
    level = this.level,
    isCompleted = this.isCompleted,
    isMe = isMe,
    userId = userId
)

@NavTypeSerializer
class MissionNavArgsSerializer : DestinationsNavTypeSerializer<MissionNavArgs> {
    override fun toRouteString(value: MissionNavArgs): String {
        return "${value.id}::${value.title}::${value.level}::${value.isCompleted}::${value.isMe}::${value.userId}"
    }

    override fun fromRouteString(routeStr: String): MissionNavArgs {
        val it = routeStr.split("::")
        return MissionNavArgs(
            id = it[0].toInt(),
            title = it[1],
            level = MissionLevel.of(it[2].toInt()),
            isCompleted = it[3].toBoolean(),
            isMe = it[4].toBoolean(),
            userId = it[5].toInt()
        )
    }
}
