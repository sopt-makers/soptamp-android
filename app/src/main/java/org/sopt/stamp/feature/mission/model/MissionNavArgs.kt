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