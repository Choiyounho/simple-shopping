package com.soten.shop.user.presentation

import com.soten.shop.auth.interceptor.SignUpService
import com.soten.shop.auth.interceptor.SignUpRequest
import com.soten.shop.common.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/soten")
class UserApiController @Autowired constructor(
    private val signupService: SignUpService
) {

    @PostMapping("/users")
    fun signup(@RequestBody signUpRequest: SignUpRequest) =
        ApiResponse.ok(signupService.signup(signUpRequest))

}
