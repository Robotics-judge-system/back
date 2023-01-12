package ru.joust.back.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import ru.joust.back.entity.User
import ru.joust.back.entity.UserStatus
import ru.joust.back.repo.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {
    fun getUserByIdOrThrow(id: Long): User = userRepository.findById(id).orElseThrow()

    fun createNewUser(
        username: String,
        email: String,
        name: String,
        rawPassword: String,
        userStatus: UserStatus = UserStatus.ACTIVE
    ): User {
        val user = User(
            username = username,
            name = name,
            email = email,
            password = bCryptPasswordEncoder.encode(rawPassword),
            userStatus = userStatus
        )
        return userRepository.save(user)
    }
}
