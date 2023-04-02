package com.example.infrastructure.users

import com.example.domains.entities.users.User
import com.example.domains.entities.users.UserRepository
import java.time.LocalDate
import org.springframework.stereotype.Repository

@Repository
class InMemoryUserRepository: UserRepository {
    override fun find(id: String) =
            User(
                id = "1",
                name = "user name",
                email = "example@gmail.com",
                birthday = LocalDate.now(),
            )
                .takeIf { id == "1" }

    override fun save(user: User) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int) {
        TODO("Not yet implemented")
    }
}
