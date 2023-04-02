package com.example.domains.entities.users

import java.time.LocalDate

class User(
    val id: String,
    val name: String,
    val email: String,
    val birthday: LocalDate,
)
