package org.sopt.stamp.login

data class SoptampLoginViewState(
    val id: String?,
    val password: String?
) {
    companion object {
        fun init() = SoptampLoginViewState(null, null)
    }
}
