package com.soten.shop.auth.interceptor

data class SignUpRequest(
    val email: String,
    val name: String,
    val password: String
)
