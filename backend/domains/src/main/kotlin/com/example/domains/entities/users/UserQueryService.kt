package com.example.domains.entities.users

interface UserQueryService {
    fun find(id: String): UserDTO?
    fun allUsers(condition: AllUsersCondition): Users
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
