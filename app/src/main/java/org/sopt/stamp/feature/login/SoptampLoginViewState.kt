package org.sopt.stamp.feature.login

data class SoptampLoginViewState(
    val id: String?,
    val password: String?,
    val errorMessage: String?
) {
    companion object {
        fun init() = SoptampLoginViewState(null, null, null)
    }
}
