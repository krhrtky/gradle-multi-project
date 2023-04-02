package com.example.domains.entities.users

interface UserRepository {
    fun find(id: String): User?
    fun save(user: User)
    fun delete(id: Int)
}
