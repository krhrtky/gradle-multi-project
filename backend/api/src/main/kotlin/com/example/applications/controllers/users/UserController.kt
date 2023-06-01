package com.example.applications.controllers.users

import com.example.domains.applications.users.UserApplicationService
import com.example.domains.applications.users.UserCreateInput
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.http.ResponseEntity.internalServerError
import org.springframework.lang.NonNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

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

    @PostMapping
    fun create(@RequestBody requestBody: CreateRequestBody) =
        requestBody
            .let {
                UserCreateInput(
                    name =  it.name,
                    email = it.email,
                )
            }
            .let(service::create)
            .map(::ok)
            .getOrElse {
                internalServerError().build()
            }
}

data class CreateRequestBody(
    @NonNull val name: String,
    @NonNull val email: String,
)
