package ru.joust.back.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import ru.joust.back.entity.User
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class JwtProvider() {

    private val key = Keys.hmacShaKeyFor(
        Decoders.BASE64.decode(
            "qBTmv4oXFFR2GwjexDJ4t6fsIUIUhhXqlktXjXdkcyygs8nPVEwMfo29VDRRepYDVV5IkIxBMzr7OEHXEHd37w=="
        )
    )

    fun generateToken(
        user: User
    ): String {
        val now = LocalDateTime.now()
        val accessExpirationTime = Date.from(
            now
                .plusSeconds(3600)
                .atZone(ZoneId.systemDefault())
                .toInstant()
        )
        return Jwts.builder()
            .setSubject("")
            .setExpiration(accessExpirationTime)
            .signWith(
                key
            )
            .claim("refresh_token_id", "-1")
            .claim("username", user.username)
            .compact()
    }

    fun validateToken(
        token: String
    ): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }

    fun getClaims(
        token: String
    ): Claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .body

}
