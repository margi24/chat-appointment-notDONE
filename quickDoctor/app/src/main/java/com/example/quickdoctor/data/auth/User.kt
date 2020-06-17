package com.example.quickdoctor.data.auth

data class User(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val type: String
)
