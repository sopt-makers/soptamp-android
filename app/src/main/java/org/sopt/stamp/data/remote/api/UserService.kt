package org.sopt.stamp.data.remote.api

import org.sopt.stamp.data.remote.model.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    // 회원가입
    @POST("/user/signup")
    suspend fun signup(
        @Header("nickname") nickname: String,
        @Header("email") email: String,
        @Header("password") password: String,
        @Header("osType") osType: String?,
        @Header("clientToken") clientToken: String?
    ): UserResponse

    // 닉네임 중복검사
    @GET("/auth?nickname={nickname}")
    suspend fun checkNickname(
        @Path("nickname") nickname: String
    ): UserResponse

    // 이메일 중복검사
    @GET("/auth?email={email}")
    suspend fun checkEmail(
        @Path("email") email: String
    ): UserResponse

    // 로그인
    @POST("/user/login")
    suspend fun login(
        @Header("email") email: String,
        @Header("password") password: String,
    ): UserResponse

    // 비밀번호 변경
    // 닉네임 변경
    // 탈퇴하기
}
