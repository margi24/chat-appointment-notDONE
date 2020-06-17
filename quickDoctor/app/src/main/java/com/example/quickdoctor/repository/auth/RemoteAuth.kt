package com.example.quickdoctor.repository.auth
import com.example.quickdoctor.core.Result

import com.example.quickdoctor.core.Api
import com.example.quickdoctor.data.request.LoginRequest
import com.example.quickdoctor.data.auth.User
import com.example.quickdoctor.data.result.LoginResult
import com.example.quickdoctor.data.result.RegisterResult
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.lang.Exception

object RemoteAuth {

    interface AuthService{
        @Headers("Content-Type: application/json")
        @POST("/auth/register")
        suspend fun register(@Body user: User): RegisterResult

        @Headers("Content-Type: application/json")
        @POST("/auth/login")
        suspend fun login(@Body loginRequest: LoginRequest): LoginResult
    }

    private val authService: AuthService = Api.retrofit.create(
        AuthService::class.java)

    suspend fun register(user: User): Result<RegisterResult>{
        return try {
            Result.Success(authService.register(user))
        }catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun login(loginRequest: LoginRequest): Result<LoginResult> {
        return try {
            Result.Success(authService.login(loginRequest))
        }catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
    }
}