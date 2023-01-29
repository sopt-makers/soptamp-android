package org.sopt.stamp.feature.login

data class SoptampLoginViewState(
    val email: String?,
    val password: String?,
    val errorMessage: String?,
    val isComplete: Boolean = false
) {
    companion object {
        fun init() = SoptampLoginViewState(null, null, null)
    }
}