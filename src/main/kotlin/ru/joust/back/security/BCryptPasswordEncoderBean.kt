package ru.joust.back.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class BCryptPasswordEncoderBean {
    @Bean
    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()
}
