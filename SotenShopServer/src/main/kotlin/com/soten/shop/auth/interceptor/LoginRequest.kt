package com.soten.shop.auth.interceptor

data class LoginRequest(
    val email: String,
    val password: String
)
