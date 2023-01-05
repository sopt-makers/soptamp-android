package org.sopt.stamp.domain.model

enum class MissionsFilter(
    val title: String
) {
    ALL_MISSION("전체 미션"),
    COMPLETE_MISSION("완료 미션"),
    INCOMPLETE_MISSION("미완료 미션");

    fun hasTitle(title: String) = (title == this.title)

    companion object {
        fun getTitleOfMissionsList(): List<String> = values().map { it.title }
        fun findFilterOf(title: String) = values().find { it.hasTitle(title) }
            ?: throw IllegalArgumentException("$title 에 해당하는 필터가 없습니다.")
    }
}
