package com.example.quickdoctor.repository.auth

import android.util.Log
import com.example.quickdoctor.core.Api
import com.example.quickdoctor.core.Result
import com.example.quickdoctor.data.request.LoginRequest
import com.example.quickdoctor.data.auth.User
import com.example.quickdoctor.data.auth.UserDTO
import com.example.quickdoctor.data.result.LoginResult
import com.example.quickdoctor.data.result.RegisterResult
import com.example.quickdoctor.extensions.TAG

object AuthRepository {

    var user: UserDTO? = null
        private set
    var loginRequest: LoginRequest? = null
    private set
    init {
        user = null
        loginRequest = null
    }
    suspend fun login(username: String, password: String): Result<LoginResult> {
        val loginData =
            LoginRequest(username, password)

        val result =  RemoteAuth.login(loginData)
        if(result is Result.Success<LoginResult>) {
            setLoggedInUser(result.data)
        }
        return result
    }

    private fun setLoggedInUser(data: LoginResult) {
        this.user = data.userDTO
        if(user?.type == "Pacient"){
            Log.v(TAG,"Pacieeeeeeeeeeeeeent")
        }
        Api.tokenInterceptor.token = data.accessToken
    }

    suspend fun register(email: String, firstName: String, lastName: String, password: String, type: String): Result<RegisterResult> {
        val user =
            User(email, firstName, lastName, password, type)
        return RemoteAuth.register(user)
    }


}
