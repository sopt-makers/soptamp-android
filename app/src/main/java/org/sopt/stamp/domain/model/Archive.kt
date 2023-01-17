package org.sopt.stamp.domain.model

data class Archive(
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val id: Int,
    val contents: String,
    val images: List<String>,
    val userId: Int,
    val missionId: Int,
)
