package ru.joust.back.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.joust.back.entity.RefreshToken

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun existsBySecret(secret: String): Boolean
    fun getByIdAndSecret(id: Long, secret: String): RefreshToken?
}
