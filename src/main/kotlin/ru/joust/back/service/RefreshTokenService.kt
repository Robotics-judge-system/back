package ru.joust.back.service

import org.springframework.stereotype.Service
import ru.joust.back.entity.RefreshToken
import ru.joust.back.entity.User
import ru.joust.back.repo.RefreshTokenRepository
import java.security.SecureRandom

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository
) {

    fun saveRefreshToken(
        secret: String,
        user: User
    ) = refreshTokenRepository.save(
        RefreshToken(
            secret = secret,
            user = user
        )
    )

    fun generateRefreshToken(
        user: User,
        secretLen: Int = 15
    ): RefreshToken {
        var token: String
        do {
            token = generateRandomRefreshToken(secretLen)
        } while (refreshTokenRepository.existsBySecret(token))
        return saveRefreshToken(token, user)
    }

    private fun generateRandomRefreshToken(
        length: Int
    ): String {
        val lowLetters = ('a'..'z').toList()
        val upperLetters = lowLetters.map {
            it.uppercaseChar()
        }.toList()
        val digits = ('0'..'9').toList()
        val alphabet = lowLetters + upperLetters + digits
        val generatedToken = StringBuilder()
        val secureRandom = SecureRandom()

        repeat(length) {
            generatedToken.append(
                alphabet[secureRandom.nextInt(alphabet.size)]
            )
        }

        return generatedToken.toString()
    }
}
