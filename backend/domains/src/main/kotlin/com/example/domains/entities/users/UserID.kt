package com.example.domains.entities.users

import java.util.UUID

@JvmInline
value class UserID  private constructor(
    private val id: UUID,
)
{
    val value
        get() = id.toString()


    companion object {
        fun create() = UUID
            .randomUUID()
            .let(::UserID)

        internal fun fromRepository(id: String) = id
            .let(UUID::fromString)
            .let(::UserID)
    }
}
