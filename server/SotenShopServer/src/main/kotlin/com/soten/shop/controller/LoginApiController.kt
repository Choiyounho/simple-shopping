package com.soten.shop.controller

import com.soten.shop.common.ApiResponse
import com.soten.shop.domain.auth.LoginRequest
import com.soten.shop.domain.auth.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/soten")
class LoginApiController @Autowired constructor(
    private val loginService: LoginService
) {

    @PostMapping("/login")
    fun signIn(@RequestBody loginRequest: LoginRequest) =
        ApiResponse.ok(loginService.signIn(loginRequest))

}
