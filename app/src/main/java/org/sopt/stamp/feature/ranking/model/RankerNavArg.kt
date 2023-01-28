package org.sopt.stamp.feature.ranking.model

import com.ramcosta.composedestinations.navargs.DestinationsNavTypeSerializer
import com.ramcosta.composedestinations.navargs.NavTypeSerializer

data class RankerNavArg(
    val userId: Int,
    val nickname: String,
    val description: String
)

fun RankerUiModel.toArgs() = RankerNavArg(
    userId = this.userId,
    nickname = this.nickname,
    description = this.getDescription()
)

@NavTypeSerializer
class RankerNavArgsSerializer : DestinationsNavTypeSerializer<RankerNavArg> {
    override fun fromRouteString(routeStr: String): RankerNavArg {
        val (userId, nickname, description) = routeStr.split("::")
        return RankerNavArg(
            userId = userId.toInt(),
            nickname = nickname,
            description = description
        )
    }

    override fun toRouteString(value: RankerNavArg): String {
        return "${value.userId}::${value.nickname}::${value.description}"
    }
}