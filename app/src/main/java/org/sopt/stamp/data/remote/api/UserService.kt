package org.sopt.stamp.data.remote.api

import org.sopt.stamp.data.remote.model.request.LoginRequest
import org.sopt.stamp.data.remote.model.request.SignUpRequest
import org.sopt.stamp.data.remote.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    // 회원가입
    @POST("user/signup")
    suspend fun signup(
        @Body request: SignUpRequest
    ): Response<UserResponse>

    // 닉네임 중복검사
    @GET("auth?nickname={nickname}")
    suspend fun checkNickname(
        @Query("nickname") nickname: String
    ): Response<UserResponse>

    // 이메일 중복검사
    @GET("auth?email={email}")
    suspend fun checkEmail(
        @Query("email") email: String
    ): Response<UserResponse>

    // 로그인
    @POST("user/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<UserResponse>

    // 비밀번호 변경
    // 닉네임 변경
    // 탈퇴하기
}
