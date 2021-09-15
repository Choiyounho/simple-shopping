package com.soten.shop.domain.auth

data class SignUpRequest(
    val email: String,
    val name: String,
    val password: String
)
