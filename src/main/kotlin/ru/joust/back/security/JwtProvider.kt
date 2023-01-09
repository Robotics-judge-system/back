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
class JwtProvider {

    private val key = Keys.hmacShaKeyFor(
        Decoders.BASE64.decode(
            "qBTmv4oXFFR2GwjexDJ4t6fsIUIUhhXqlktXjXdkcyygs8nPVEwMfo29VDRRepYDVV5IkIxBMzr7OEHXEHd37w=="
        )
    )

    fun generateToken(
        user: User,
        refreshTokenId: Long
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
            .claim("refresh_token_id", refreshTokenId)
            .claim("username", user.username)
            .compact()
    }

    fun generateRefreshToken(
        secret: String,
        refreshTokenId: Long,
        userId: Long
    ): String {
        return Jwts.builder()
            .setSubject(secret)
            .setExpiration(null)
            .signWith(key)
            .claim("id", refreshTokenId)
            .claim("user_id", userId)
            .compact()
    }

    fun refreshTokenInfo(
        token: String
    ): Triple<Long, Long, String> {
        val body = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
        return Triple(
            castUtil(body["id"]),
            castUtil(body["user_id"]),
            body.subject as String,
        )
    }

    private fun castUtil(
        value:Any?
    ) = when(value){
        is Int -> value.toLong()
        is Long -> value
        else -> throw Exception()
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
