package org.sopt.stamp.feature.signup

data class SoptampSignUpViewState(
    val nickname: String?,
    val email: String?,
    val password: String?,
    val passwordConfirm: String?,
    val errorMessage: String?
) {
    companion object {
        fun init() = SoptampSignUpViewState(null, null, null, null, null)
    }
}
