package org.sopt.stamp.domain.usecase

import javax.inject.Inject

class AutoLoginUseCase @Inject constructor(
    private val getUserIdUseCase: GetUserIdUseCase
) {
    operator fun invoke() = getUserIdUseCase() != -1
}
