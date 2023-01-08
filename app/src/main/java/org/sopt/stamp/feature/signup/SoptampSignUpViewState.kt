package org.sopt.stamp.feature.signup

data class SoptampSignUpViewState(
    val id: String?,
    val password: String?
) {
    companion object {
        fun init() = SoptampSignUpViewState(null, null)
    }
}