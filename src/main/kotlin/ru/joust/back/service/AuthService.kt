package ru.joust.back.service

import jakarta.security.auth.message.AuthException
import org.springframework.stereotype.Service
import ru.joust.back.dto.LoginRequestDto
import ru.joust.back.dto.LoginResponseDto
import ru.joust.back.repo.UserRepository
import ru.joust.back.security.JwtProvider

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider
) {
    fun login(loginRequestDto: LoginRequestDto): LoginResponseDto {
        val user = userRepository.findByUsername(loginRequestDto.login)
            ?: throw AuthException("no such user")
        if (user.password != loginRequestDto.password) {
            throw AuthException("incorrect login\\password")
        }
        return LoginResponseDto(
            jwtProvider.generateToken(user),
            ""
        )
    }
}
