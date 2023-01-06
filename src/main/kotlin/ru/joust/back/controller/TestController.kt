package ru.joust.back.controller

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class TestController {
    @QueryMapping
    fun test(
        @Argument count: Int
    ): String = "asd"
}
