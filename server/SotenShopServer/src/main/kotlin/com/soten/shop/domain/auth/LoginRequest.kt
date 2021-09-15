package com.soten.shop.domain.auth

data class LoginRequest(
    val email: String,
    val password: String
)
