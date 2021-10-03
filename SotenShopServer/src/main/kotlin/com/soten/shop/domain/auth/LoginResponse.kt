package com.soten.shop.domain.auth

data class LoginResponse(
    val token: String,
    val refreshToken: String,
    val userName: String,
    val userId: Int
)
