package com.example.applications.controllers.graphql

import com.example.applications.graphql.types.CreateUserInput
import com.example.applications.graphql.types.User
import com.example.domains.applications.users.UserApplicationService
import com.example.domains.applications.users.UserCreateInput
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class GraphQLController(
    private val service: UserApplicationService,
) {
    @DgsQuery
    fun fetchUser(@InputArgument id: String): User? =
        service
            .find(id)
            .getOrNull()
            ?.map(::User)

    @DgsMutation
    fun createUser(@InputArgument input: CreateUserInput): User =
        UserCreateInput(
            name = input.name,
            email = input.email,
        )
            .let(service::create)
            .getOrThrow()
            .map(::User)
}
