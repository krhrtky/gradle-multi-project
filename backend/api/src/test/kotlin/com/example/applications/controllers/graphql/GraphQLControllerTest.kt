package com.example.applications.controllers.graphql

import com.example.domains.entities.users.UserDTO
import com.example.domains.entities.users.UserQueryService
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration
import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.beEqual
import io.kotest.matchers.should
import io.mockk.clearAllMocks
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import com.example.applications.graphql.types.User as GQLUser

@SpringBootTest(
    classes = [
        DgsAutoConfiguration::class,
        GraphQLController::class,
    ]
)
class GraphQLControllerTest(
    @Autowired
    private val queryExecutor: DgsQueryExecutor,
    @MockkBean
    private val userQueryService: UserQueryService,
) : DescribeSpec({
    describe(".fetchUser") {
        beforeEach {
            clearAllMocks()
        }
        it("return exists user") {
            every {
                userQueryService
                    .find("2467240f-5d27-4e42-946e-397509a74b7a")
            } returns UserDTO(
                "2467240f-5d27-4e42-946e-397509a74b7a",
                "test user",
                "test.user@example.com",
            )
            val query =
                """
                    query {
                      fetchUser(id:"2467240f-5d27-4e42-946e-397509a74b7a") {
                        id
                        name
                        email
                        __typename
                      }
                    }
                """.trimIndent()

            val result = queryExecutor
                .executeAndExtractJsonPath<Map<String, String>>(
                    query,
                    "data.fetchUser",
                )

            assertSoftly(result) {
                result["id"] should beEqual("2467240f-5d27-4e42-946e-397509a74b7a")
                result["name"] should beEqual("test user")
                result["email"] should beEqual("test.user@example.com")
                result["__typename"] should beEqual("User")
            }
        }
        it("return null when does not exists user") {
            every {
                userQueryService
                    .find("2467240f-5d27-4e42-946e-397509a74b7a")
            } returns null

            val query =
                """
                    query {
                      fetchUser(id:"2467240f-5d27-4e42-946e-397509a74b7a") {
                        id
                        name
                        email
                        __typename
                      }
                    }
                """.trimIndent()

            val result: GQLUser? = queryExecutor
                .executeAndExtractJsonPath(
                    query,
                    "data.fetchUser",
                )

            result should beEqual(null)
        }
    }
})
