package com.maha.checkout.infrastructure.adapter.outbound.db

import com.maha.checkout.WithDatabase
import com.maha.checkout.domain.model.watches.BaseWatch
import com.maha.checkout.domain.model.watches.Discount
import com.maha.checkout.domain.model.watches.WatchWithDiscount
import com.maha.checkout.infrastructure.adapter.inbound.http.NotFoundException
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@WithDatabase
internal class H2WatchesPortTest {

    @Autowired
    private lateinit var h2WatchesPort: H2WatchesPort

    @Test
    fun `should return simple watch and watch with discount instances`() = runBlocking {
        // given
        val ids = setOf("001", "003")
        val expected = listOf(
            WatchWithDiscount("001", 100, Discount(3, 200)),
            BaseWatch("003", 50),
        )

        // when
        val actual = h2WatchesPort.findByIds(ids)

        // then
        actual shouldBe expected
    }

    @Test
    fun `should return empty list if passed empty set`() = runBlocking {
        // given
        val ids = emptySet<String>()
        val expected = emptyList<String>()

        // when
        val actual = h2WatchesPort.findByIds(ids)

        // then
        actual shouldBe expected
    }

    @Test
    fun `should throw NotFoundException if not all watches persisted`() = runBlocking {
        // given
        val ids = setOf("001", "not_exist")

        // when
        val actual = shouldThrow<NotFoundException> {
            h2WatchesPort.findByIds(ids)
        }

        // then
        actual.message shouldBe "Failed to find watches in catalog by ids: '[not_exist]'"
    }
}
