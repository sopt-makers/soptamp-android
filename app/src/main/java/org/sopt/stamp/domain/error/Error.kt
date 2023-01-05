package org.sopt.stamp.domain.error

sealed class Error(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {
    object NetworkUnavailable : Error()
}
