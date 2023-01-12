package ru.joust.back.controller

import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.joust.back.dto.*
import ru.joust.back.service.AuthService
import ru.joust.back.service.UserService

@RestController
@RequestMapping("api/v1")
class AuthController(
    private val authService: AuthService,
    private val userService: UserService,
) {
    @PostMapping("auth/login")
    fun login(
        @RequestBody loginRequestDto: LoginRequestDto
    ): LoginResponseDto {
        return authService.login(loginRequestDto)
    }

    @PostMapping("auth/refresh")
    fun refresh(
        @RequestBody refreshTokenDto: RefreshTokenDto
    ): LoginResponseDto {
        return authService.generateNewToken(refreshTokenDto)
    }

    @PostMapping("user/register")
    fun register(
        @RequestBody userRegistrationRequestDto: UserRegistrationRequestDto,
    ): ResponseEntity<StatusDto> = try {
        userService.createNewUser(
            name = userRegistrationRequestDto.name,
            username = userRegistrationRequestDto.username,
            email = userRegistrationRequestDto.email,
            rawPassword = userRegistrationRequestDto.password
        )
        ResponseEntity.ok(StatusDto("ok"))
    } catch (e: ConstraintViolationException) {
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            StatusDto("email is not valid")
        )
    } catch (e: DataIntegrityViolationException) {
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            StatusDto("email/username not unique")
        )
    }


    @PostMapping("test")
    fun testEndpoint(): String {
        return "hello world"
    }
}
