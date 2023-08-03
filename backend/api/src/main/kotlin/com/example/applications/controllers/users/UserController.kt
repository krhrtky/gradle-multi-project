package com.example.applications.controllers.users

import com.example.domains.applications.users.UserApplicationService
import com.example.domains.applications.users.UserCreateInput
import com.example.infrastructure.users.AllUsersCondition
import com.example.infrastructure.users.UserQueryService
import com.example.infrastructure.users.Users
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity.of
import org.springframework.http.ResponseEntity.ok
import org.springframework.lang.NonNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(
    private val service: UserApplicationService,
    private val queryService: UserQueryService,
) {
    @GetMapping("/{id}")
    fun find(@PathVariable id: String) =
        service
            .find(id)
            .map(::ok)
            .getOrElse {
                ProblemDetail
                    .forStatusAndDetail(HttpStatus.NOT_FOUND, it.message ?: "")
                    .let(::of)
                    .build()
            }

    @PostMapping
    fun create(@RequestBody requestBody: CreateRequestBody) =
        requestBody
            .let {
                UserCreateInput(
                    name = it.name,
                    email = it.email,
                )
            }
            .let(service::create)
            .map(::ok)
            .getOrElse {
                ProblemDetail
                    .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, it.message ?: "")
                    .let(::of)
                    .build()
            }
    fun list(
        @RequestParam("limit") limit: Long?,
        @RequestParam("offset") offset: Long?,
    ) = AllUsersCondition(
        limit = limit ?: 10,
        offset = offset ?: 0,
    )
        .let(queryService::allUsers)
        .let(::ok)
}

data class CreateRequestBody(
    @NonNull val name: String,
    @NonNull val email: String,
)
