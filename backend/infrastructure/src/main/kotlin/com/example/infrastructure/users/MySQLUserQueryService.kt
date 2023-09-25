package com.example.infrastructure.users

import com.example.domains.entities.users.AllUsersCondition
import com.example.domains.entities.users.UserDTO
import com.example.domains.entities.users.UserQueryService
import com.example.domains.entities.users.Users
import com.example.infrastructure.db.tables.User.Companion.USER
import org.jooq.DSLContext
import org.springframework.stereotype.Service

@Service
class MySQLUserQueryService(
    private val context: DSLContext,
) : UserQueryService {
    override fun find(id: String): UserDTO? =
        context
            .selectFrom(USER)
            .where(
                USER.ID.eq(id)
            )
            .fetchOne {
                UserDTO(
                    id = it.id,
                    name = it.name,
                    email = it.email,
                )
            }
    override fun allUsers(condition: AllUsersCondition): Users {
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
