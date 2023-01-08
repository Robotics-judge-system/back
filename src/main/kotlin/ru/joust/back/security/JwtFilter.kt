package ru.joust.back.security

import jakarta.security.auth.message.AuthException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import ru.joust.back.repo.UserRepository

@Component
class JwtFilter(
    private val jwtProvider: JwtProvider,
    private val userRepository: UserRepository
) : GenericFilterBean() {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val token = getTokenFromRequest(request as HttpServletRequest)
        if (token != null && jwtProvider.validateToken(token)) {
            val claims = jwtProvider.getClaims(token)
            val user = userRepository.findByUsername(claims["username"] as String)!!
            val jwtAuthentication = JwtAuthentication(user.username)
            jwtAuthentication.isAuthenticated = true
            SecurityContextHolder.getContext().authentication = jwtAuthentication
        }
        chain.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val headerValue = request.getHeader("Authorization")
        if (StringUtils.hasText(headerValue) && headerValue.startsWith("Bearer ")) {
            return headerValue.substring(7)
        }
        return null
    }
}
