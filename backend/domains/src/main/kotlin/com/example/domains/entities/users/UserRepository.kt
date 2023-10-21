package com.example.domains.entities.users

abstract class UserRepository {
    abstract fun find(id: String): User?
    protected abstract fun upsert(raw: UserRaw)
    fun save(user: User) {
        UserRaw(
            id = user.id.value,
            name = user.name,
            email = user.email
        )
            .let(::upsert)
    }

    protected fun mapToEntity(
        id: String,
        name: String,
        email: String,
    ): User = User.fromRepository(
        id,
        name,
        email,
    )

    protected data class UserRaw(
        val id: String,
        val name: String,
        val email: String,
    )
}
