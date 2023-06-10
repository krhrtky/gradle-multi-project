package com.example.domains.entities.users

import java.util.UUID

class User private constructor(
    val id: String,
    val name: String,
    val email: String,
    private val event: List<UserDomainEvent>,
    private var eventConsumed: Boolean = false
) {
    fun changeName(newName: String) =
        update(name = newName)
    fun changeEmail(newEmail: String) =
        update(
            email = newEmail,
            event =
            UserEmailUpdatedEvent(
                id,
                email,
                newEmail,
            )
                .let(::listOf)
        )

    fun <T> map(mapper: (
        id: String,
        name: String,
        email: String,
    ) -> T ) : T = mapper(
        id,
        name,
        email,
    )

    fun getEvent() =
        if (eventConsumed) {
            throw EventAlreadyConsumedException()
        } else {
            eventConsumed = false
            event
        }

    private fun update(
        name: String? = null,
        email: String? = null,
        event: List<UserDomainEvent> = emptyList(),
    ) = User(
        id,
        name ?: this.name,
        email ?: this.email,
        this.event + event,
    )

    private class EventAlreadyConsumedException: Exception("Event has already consumed.")

    companion object {
        fun create(
            name: String,
            email: String,
        ) =
            UUID
                .randomUUID()
                .toString()
                .let {
                    User(
                        it,
                        name,
                        email,
                        UserCreatedEvent(
                            it,
                            email,
                        )
                            .let(::listOf)
                    )
                }

        fun fromRepository(
            id: String,
            name: String,
            email: String,
        ) = User(
            id,
            name,
            email,
            emptyList(),
        )
    }
}

interface UserDomainEvent

data class UserCreatedEvent(
    val userId: String,
    val email: String,
): UserDomainEvent

data class UserEmailUpdatedEvent(
    val userId: String,
    val beforeEmail: String,
    val afterEmail: String,
): UserDomainEvent
