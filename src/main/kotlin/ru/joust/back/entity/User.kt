package ru.joust.back.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import java.time.Clock
import java.time.Instant

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "username")
    var username: String = "",

    @Column(name = "email")
    @get:Email
    val email: String = "",

    @Column(name = "name")
    var name: String = "",


    password: String = "",

    @Column(name = "registered")
    val registered: Instant = Clock.systemDefaultZone().instant(),

    @Column(name = "password_updated")
    private var passwordUpdated: Instant = Clock.systemDefaultZone().instant(),

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    var userStatus: UserStatus = UserStatus.NOT_CONFIRMED,
) {
    @Column(name = "password")
    var password: String = password
        set(value) {
            passwordUpdated = Clock.systemDefaultZone().instant()
            field = value
        }
}
