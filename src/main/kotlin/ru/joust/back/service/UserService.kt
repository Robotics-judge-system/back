package ru.joust.back.service

import org.springframework.stereotype.Service
import ru.joust.back.entity.User
import ru.joust.back.repo.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun getUserByIdOrThrow(id: Long): User = userRepository.findById(id).orElseThrow()
}
