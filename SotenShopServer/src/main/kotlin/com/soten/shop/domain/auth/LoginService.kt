package com.soten.shop.domain.auth

import com.soten.shop.common.ShopException
import com.soten.shop.domain.user.User
import com.soten.shop.domain.user.UserRepository
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LoginService @Autowired constructor(
    private val userRepository: UserRepository
) {

    fun signIn(loginRequest: LoginRequest): LoginResponse {
        val user = userRepository
            .findByEmail(loginRequest.email.toLowerCase())
            ?: throw ShopException("로그인 정보를 확인해주세요.")

        if (isNotValidPassword(loginRequest.password, user.password)) {
            throw ShopException("로그인 정보를 확인해주세요.")
        }

        return responseWithTokens(user)
    }

    private fun isNotValidPassword(
        plain: String,
        hashed: String
    ) = BCrypt.checkpw(plain, hashed).not()

    private fun responseWithTokens(user: User) = user.id?.let { userId ->
        LoginResponse(
            JwtUtil.createToken(user.email),
            JwtUtil.createRefreshToken(user.email),
            user.name,
            userId
        )
    } ?: throw IllegalStateException("user.id 없음.")

}
