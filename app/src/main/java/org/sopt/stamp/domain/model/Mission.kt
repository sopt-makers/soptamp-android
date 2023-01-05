package org.sopt.stamp.domain.model // ktlint-disable filename

data class Mission(
    val id: Int,
    val title: String,
    val level: Int,
    val profileImage: List<String>?,
    val isCompleted: Boolean
)
