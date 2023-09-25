package com.example.applications.users

import com.example.domains.entities.users.User
import com.example.domains.entities.users.UserRepository
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.springframework.stereotype.Service

@Service
class UserApplicationService(
    private val repository: UserRepository,
) {
    fun find(id: String) = repository
        .find(id)
        ?.let(::Ok)
        ?: UserDoesNotFindException("User(id = $id) does not exists.")
            .let(::Err)

    fun create(input: UserCreateInput) = {
        input
            .let {
                User.create(input.name, input.email)
            }
            .apply(repository::save).id.value
    }
        .let(::runCatching)
}

class UserDoesNotFindException(override val message: String?) : Exception(message)

data class UserCreateInput(
    val name: String,
    val email: String,
)
