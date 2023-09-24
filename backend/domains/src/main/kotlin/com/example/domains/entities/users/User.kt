package com.example.domains.entities.users

class User private constructor(
    val id: UserID,
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
                id.value,
                email,
                newEmail,
            )
                .let(::listOf)
        )

    fun <T> map(
        mapper: (
            id: String,
            name: String,
            email: String,
        ) -> T
    ): T = mapper(
        id.value,
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

    private class EventAlreadyConsumedException : Exception("Event has already consumed.")

    companion object {
        fun create(
            name: String,
            email: String,
        ) =
            UserID
                .create()
                .let {
                    User(
                        it,
                        name,
                        email,
                        UserCreatedEvent(
                            it.value,
                            email,
                        )
                            .let(::listOf)
                    )
                }

        internal fun fromRepository(
            id: String,
            name: String,
            email: String,
        ) = User(
            UserID.fromRepository(id),
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
) : UserDomainEvent

data class UserEmailUpdatedEvent(
    val userId: String,
    val beforeEmail: String,
    val afterEmail: String,
) : UserDomainEvent
