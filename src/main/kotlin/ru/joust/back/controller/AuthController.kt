package ru.joust.back.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.joust.back.dto.LoginRequestDto
import ru.joust.back.dto.LoginResponseDto
import ru.joust.back.dto.RefreshTokenDto
import ru.joust.back.service.AuthService

@RestController
@RequestMapping("api/v1")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("login")
    fun login(
        @RequestBody loginRequestDto: LoginRequestDto
    ): LoginResponseDto {
        return authService.login(loginRequestDto)
    }

    @PostMapping("refresh")
    fun refresh(
        @RequestBody refreshTokenDto: RefreshTokenDto
    ): LoginResponseDto {
        return authService.generateNewToken(refreshTokenDto)
    }

    @PostMapping("test")
    fun testEndpoint(): String {
        return "hello world"
    }
}
