package com.soten.shop.controller

import com.soten.shop.common.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestApiController {

    @GetMapping("/soten/test")
    fun hello() = ApiResponse.ok("test")

}
