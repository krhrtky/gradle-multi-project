package com.example.applications.controllers.users

import com.example.domains.applications.users.UserApplicationService
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val service: UserApplicationService,
) {
    @GetMapping("/{id}")
    fun find(@PathVariable id: String)=
        service
            .find(id)
            .map(::ok)
            .getOrElse { notFound().build() }
}
