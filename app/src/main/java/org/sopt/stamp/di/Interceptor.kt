package org.sopt.stamp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Logging

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Auth
