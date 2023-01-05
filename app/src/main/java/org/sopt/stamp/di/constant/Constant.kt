package org.sopt.stamp.di.constant

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Strings(val value: Constant)

enum class Constant {
    SOPTAMP_API_KEY
}
