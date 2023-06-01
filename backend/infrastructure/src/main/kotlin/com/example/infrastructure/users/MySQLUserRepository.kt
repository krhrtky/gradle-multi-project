package com.example.infrastructure.users

import com.example.domains.entities.users.User
import com.example.domains.entities.users.UserRepository
import com.example.infrastructure.db.tables.User.Companion.USER
import com.example.infrastructure.db.tables.records.UserRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class MySQLUserRepository(
    private val context: DSLContext,
) : UserRepository {
    override fun find(id: String) =
        context
            .select(USER.asterisk())
            .from(USER)
            .where(
                USER.ID.eq(id)
            )
            .fetchOneInto(USER)
            ?.let {
                User
                    .fromRepository(
                        id = it.id,
                        name = it.name,
                        email = it.email,
                    )
            }

    override fun save(newUser: User) {
        newUser
            .map { id, name, email ->
                UserRecord(
                    id = id,
                    name = name,
                    email = email,
                )
            }
            .let {
                context
                    .insertInto(USER)
                    .set(it)
                    .onDuplicateKeyUpdate()
                    .set(USER.UPDATED_AT, LocalDateTime.now())
                    .execute()
            }

    }
}
