package ru.joust.back.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.joust.back.entity.UserStatus
import ru.joust.back.repo.UserRepository
import ru.joust.back.security.JwtUserDetails

@Service
class JwtUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException(
            "Username not found"
        )
        if (user.userStatus != UserStatus.ACTIVE) {
            throw UsernameNotFoundException("User not active")
        }
        return JwtUserDetails.fromUser(user)
    }
}
