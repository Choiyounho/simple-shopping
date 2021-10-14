package com.soten.shop.user.presentation

import com.soten.shop.auth.interceptor.SignUpService
import com.soten.shop.auth.interceptor.SignUpRequest
import com.soten.shop.common.ApiResponse
import com.soten.shop.user.application.UserService
import com.soten.shop.user.dto.CardNameUpdateRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/soten")
class UserApiController @Autowired constructor(
    private val signUpService: SignUpService,
    private val userService: UserService
) {

    @PostMapping("/sign-up")
    fun signup(@RequestBody signUpRequest: SignUpRequest) =
        ApiResponse.ok(signUpService.signup(signUpRequest))

    @PatchMapping("/register/card")
    fun registerCard(@RequestBody cardNameUpdateRequest: CardNameUpdateRequest) =
        ApiResponse.ok(userService.updateCardName(cardNameUpdateRequest))

}
