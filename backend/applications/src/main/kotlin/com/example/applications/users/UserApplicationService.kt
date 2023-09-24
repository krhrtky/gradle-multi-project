package com.example.applications.users

import com.example.domains.entities.users.User
import com.example.domains.entities.users.UserRepository
import org.springframework.stereotype.Service

@Service
class UserApplicationService(
    private val repository: UserRepository,
) {
    fun find(id: String) = repository
        .find(id)
        ?.let { Result.success(it) }
        ?: Result
            .failure(UserDoesNotFindException("User(id = $id) does not exists."))

    fun create(input: UserCreateInput) = {
        input
            .let {
                User.create(input.name, input.email)
            }
            .apply(repository::save)
    }
        .let(::runCatching)
}

class UserDoesNotFindException(override val message: String?) : Exception(message)

data class UserCreateInput(
    val name: String,
    val email: String,
)
