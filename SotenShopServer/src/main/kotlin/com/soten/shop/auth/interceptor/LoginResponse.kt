package com.soten.shop.auth.interceptor

data class LoginResponse(
    val token: String,
    val refreshToken: String,
    val userName: String,
    val userId: Int
)
