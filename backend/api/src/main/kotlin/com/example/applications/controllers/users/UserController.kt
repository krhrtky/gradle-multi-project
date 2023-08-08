package com.example.applications.controllers.users

import com.example.domains.applications.users.UserApplicationService
import com.example.domains.applications.users.UserCreateInput
import com.example.infrastructure.users.AllUsersCondition
import com.example.infrastructure.users.UserQueryService
import com.example.infrastructure.users.Users
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
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
            .map {
                FindUserSuccessResponse(
                    id = it.id,
                    name = it.name,
                    email = it.email,
                )
            }
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
            .map {
                CreateUserSuccessResponse(
                    it.id
                )
            }
            .map(::ok)
            .getOrElse {
                ProblemDetail
                    .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, it.message ?: "")
                    .let(::of)
                    .build()
            }

    @GetMapping
    @Operation(
        summary = "ユーザー一覧",
        tags = ["user"],
        description = "ユーザー一覧",
    )
    @ApiResponse(
        responseCode = "200",
        description = "ユーザー一覧",
        content = [
            Content(
                mediaType = "application/json",
                schema = Schema(implementation = Users::class),
            )
        ]
    )
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

data class FindUserSuccessResponse(
    val id: String,
    val name: String,
    val email: String,
)

data class CreateUserSuccessResponse(
    val id: String,
)

data class CreateRequestBody(
    @NonNull val name: String,
    @NonNull val email: String,
)
