package org.sopt.stamp.feature.mission

enum class MissionFilter(
    val text: String,
    val path: String,
    var isSelected: Boolean
) {
    ALL("전체 미션", "all", true),
    COMPLETE("완료 미션", "complete", false),
    INCOMPLETE("미완료 미션", "incomplete", false);

    fun select() {
        values().map { it.isSelected = false }
        this.isSelected = true
    }
}
