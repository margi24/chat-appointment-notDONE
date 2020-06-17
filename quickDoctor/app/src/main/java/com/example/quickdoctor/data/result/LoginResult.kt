package com.example.quickdoctor.data.result

import com.example.quickdoctor.data.auth.UserDTO

data class LoginResult(val accessToken: String, val refreshToken: String, val userDTO: UserDTO)