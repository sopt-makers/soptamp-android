package org.sopt.stamp.config.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

/*
* Splash와 Onboarding 포함한 Screen
* */
@RootNavGraph(start = true)
@NavGraph
annotation class Initializer(
    val start: Boolean = false
)

@RootNavGraph
@NavGraph
annotation class Auth(
    val start: Boolean = false
)

@RootNavGraph
@NavGraph
annotation class Mission(
    val start: Boolean = false
)

@RootNavGraph
@NavGraph
annotation class Setting(
    val start: Boolean = false
)
