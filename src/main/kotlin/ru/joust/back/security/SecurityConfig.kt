package ru.joust.back.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val jwtFilter: JwtFilter
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/api/v1/login").permitAll()
                    .requestMatchers("/api/v1/refresh").permitAll()
                    .requestMatchers("/api/v1/user/register").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            }.build()
    }
}
