package ru.joust.back.service

import jakarta.security.auth.message.AuthException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.joust.back.dto.LoginRequestDto
import ru.joust.back.dto.LoginResponseDto
import ru.joust.back.repo.UserRepository
import ru.joust.back.security.JwtProvider

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val refreshTokenService: RefreshTokenService
) {
    @Transactional
    fun login(loginRequestDto: LoginRequestDto): LoginResponseDto {
        val user = userRepository.findByUsername(loginRequestDto.login)
            ?: throw AuthException("no such user")
        if (user.password != loginRequestDto.password) {
            throw AuthException("incorrect login\\password")
        }

        val refreshToken = refreshTokenService.generateRefreshToken(user)
        val jwtRefreshToken = jwtProvider.generateRefreshToken(
            refreshToken.secret,
            refreshToken.id
        )

        return LoginResponseDto(
            jwtProvider.generateToken(user, 2),
            jwtRefreshToken
        )
    }
}
