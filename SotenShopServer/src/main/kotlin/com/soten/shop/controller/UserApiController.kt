package com.soten.shop.controller

import com.soten.shop.domain.auth.SignUpService
import com.soten.shop.domain.auth.SignUpRequest
import com.soten.shop.common.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/soten")
class UserApiController @Autowired constructor(
    private val signUpService: SignUpService
) {

    @PostMapping("/sign-up")
    fun signup(@RequestBody signUpRequest: SignUpRequest) =
        ApiResponse.ok(signUpService.signup(signUpRequest))

}
