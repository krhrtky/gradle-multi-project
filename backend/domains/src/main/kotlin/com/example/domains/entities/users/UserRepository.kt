package com.example.domains.entities.users

abstract class UserRepository {
    abstract fun find(id: String): User?
    protected abstract fun upsert(raw: UserRaw)
    fun save(newUser: User) {
        newUser
            .map(::mapToRecord)
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

    private fun mapToRecord(
        id: String,
        name: String,
        email: String,
    ) = UserRaw(
        id = id,
        name = name,
        email = email,
    )

    protected data class UserRaw(
        val id: String,
        val name: String,
        val email: String,
    )
}
