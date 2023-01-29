package org.sopt.stamp.domain

class MissionLevel private constructor(
    val value: Int
) {
    override fun toString(): String {
        return "$value"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MissionLevel

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value
    }

    companion object {
        const val MINIMUM_LEVEL = 1
        const val MAXIMUM_LEVEL = 3

        fun of(level: Int): MissionLevel {
            validateMissionLevel(level)
            return MissionLevel(level)
        }

        private fun validateMissionLevel(level: Int) {
            require(level in MINIMUM_LEVEL..MAXIMUM_LEVEL) {
                "Mission Level 은 $MINIMUM_LEVEL ~ $MAXIMUM_LEVEL 사이 값이어야 합니다."
            }
        }
    }
}