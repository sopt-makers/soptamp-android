package org.sopt.stamp.designsystem.component.mission

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.sopt.stamp.R
import org.sopt.stamp.designsystem.style.SoptTheme
import org.sopt.stamp.domain.MissionLevel

enum class Stamp(
    val missionLevel: MissionLevel,
    @RawRes val lottie: Int
) {
    LEVEL1(MissionLevel.of(1), R.raw.pinkstamps),
    LEVEL2(MissionLevel.of(2), R.raw.purplestamp),
    LEVEL3(MissionLevel.of(3), R.raw.greenstamp);

    val starColor: Color
        @Composable get() = when (this) {
            LEVEL1 -> SoptTheme.colors.pink300
            LEVEL2 -> SoptTheme.colors.purple300
            LEVEL3 -> SoptTheme.colors.mint300
        }

    val background: Color
        @Composable get() = when (this) {
            LEVEL1 -> SoptTheme.colors.pink100
            LEVEL2 -> SoptTheme.colors.purple100
            LEVEL3 -> SoptTheme.colors.mint100
        }

    fun hasStampLevel(level: MissionLevel): Boolean {
        return this.missionLevel == level
    }

    companion object {
        val defaultStarColor: Color
            @Composable get() = SoptTheme.colors.onSurface30

        fun findStampByLevel(level: MissionLevel): Stamp = values().find {
            it.hasStampLevel(level)
        } ?: throw IllegalArgumentException("$level 에 해당하는 Stamp 가 없습니다.")
    }
}