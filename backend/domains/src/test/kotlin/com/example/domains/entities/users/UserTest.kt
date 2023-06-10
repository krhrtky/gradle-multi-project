package com.example.domains.entities.users

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkStatic
import java.util.UUID

class UserTest : DescribeSpec({
    beforeSpec {
        val mockedId = UUID.fromString("2467240f-5d27-4e42-946e-397509a74b7a")
        mockkStatic(UUID::class)
        every { UUID.randomUUID() } returns mockedId
    }

    afterSpec {
        clearAllMocks()
    }

    describe(".changeName") {
        val user = User.create(
            name = "test user",
            email = "test.user@example.com"
        )
        it("change name property value") {
            val changed = user.changeName("rename user")

            assertSoftly(changed) {
                name shouldBe "rename user"
            }
        }
        it("not change email") {
            val changed = user.changeName("rename user")

            assertSoftly(changed) {
                email shouldBe "test.user@example.com"
            }
        }
    }
    describe(".changeEmail") {
        val user = User.create(
            name = "test user",
            email = "test.user@example.com"
        )
        it("change email property value") {
            val changed = user.changeEmail("rename.user@example.com")

            assertSoftly(changed) {
                email shouldBe "rename.user@example.com"
            }
        }
        it("not change email") {
            val changed = user.changeEmail("rename.user@example.com")

            assertSoftly(changed) {
                name shouldBe "test user"
            }
        }
        it("occurred event") {
            val changed = user.changeEmail("rename.user@example.com")
            val event = changed.getEvent()

            event should {
                it shouldContain UserEmailUpdatedEvent(
                    userId = "2467240f-5d27-4e42-946e-397509a74b7a",
                    beforeEmail = "test.user@example.com",
                    afterEmail = "rename.user@example.com"
                )
            }
        }
    }
})
