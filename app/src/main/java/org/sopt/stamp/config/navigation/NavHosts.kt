package org.sopt.stamp.config.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

/*
* Splash와 Onboarding 포함한 Screen
* */
@RootNavGraph
@NavGraph
annotation class AuthNavGraph(
    val start: Boolean = false
)

@RootNavGraph(start = true)
@NavGraph
annotation class MissionNavGraph(
    val start: Boolean = false
)

@RootNavGraph
@NavGraph
annotation class SettingNavGraph(
    val start: Boolean = false
)

@RootNavGraph
@NavGraph
annotation class LoginNavGraph(
    val start: Boolean = false
)

@RootNavGraph
@NavGraph
annotation class SignUpNavGraph(
    val start: Boolean = false
)