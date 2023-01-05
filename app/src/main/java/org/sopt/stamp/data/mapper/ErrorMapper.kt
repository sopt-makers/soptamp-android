package org.sopt.stamp.data.mapper // ktlint-disable filename

import org.sopt.stamp.data.error.ErrorData
import org.sopt.stamp.domain.error.Error

internal fun ErrorData.toDomain(): Error = when (this) {
    ErrorData.NetworkUnavailable -> Error.NetworkUnavailable
}
