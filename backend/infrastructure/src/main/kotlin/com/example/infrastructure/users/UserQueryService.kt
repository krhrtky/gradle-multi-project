package com.example.infrastructure.users

import com.example.infrastructure.db.tables.User.Companion.USER
import org.jooq.DSLContext
import org.springframework.stereotype.Service

@Service
class UserQueryService(
    private val context: DSLContext,
) {
    fun allUsers(condition: AllUsersCondition): Users {
        val total = context.fetchCount(USER)
        val users = context
            .selectFrom(USER)
            .orderBy(USER.ID.desc())
            .limit(condition.limit)
            .offset(condition.offset)
            .fetchInto(USER)
            .map {
                UserDTO(
                    id = it.id,
                    name = it.name,
                    email = it.email,
                )
            }
        return Users(
            total = total.toLong(),
            users = users,
        )
    }
}

data class AllUsersCondition(
    val limit: Long,
    val offset: Long,
)

data class UserDTO(
    val id: String,
    val name: String,
    val email: String,
)

data class Users(
    val total: Long,
    val users: List<UserDTO>
)
