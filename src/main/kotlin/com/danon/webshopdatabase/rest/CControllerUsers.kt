package com.danon.webshopdatabase.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("users")
class CControllerUsers {
    @GetMapping
    fun hello() : String{
        return "Hello world"
    }
}