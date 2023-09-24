package com.example.domains.entities.users

abstract class UserRepository {
    abstract fun find(id: String): User?
    abstract fun save(newUser: User)

    protected fun mapToEntity(
        id: String,
        name: String,
        email: String,
    ): User = User.fromRepository(
        id,
        name,
        email,
    )
}
