package ru.joust.back.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {
    @PostMapping("register")
    fun register() {
        print("register")
    }
}