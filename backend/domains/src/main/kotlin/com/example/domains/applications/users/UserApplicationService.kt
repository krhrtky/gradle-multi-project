package com.example.domains.applications.users

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
}

class UserDoesNotFindException(override val message: String?): Exception(message)
