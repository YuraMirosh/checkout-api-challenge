package com.maha.checkout.domain.model.watches

import com.maha.checkout.watch
import com.maha.checkout.watchWithDiscount
import io.kotlintest.data.suspend.forall
import io.kotlintest.shouldBe
import io.kotlintest.tables.row
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class DiscountServiceTest {

    private val discountService = DiscountService()

    @Test
    fun `should return expected discount`() = runBlocking {
        forall(
            row(emptyMap(), 0),
            row(mapOf(watch(100) to 0), 0),
            row(mapOf(watch(100) to 100), 0),
            row(mapOf(watchWithDiscount(100, 3, 10) to 2), 0),
            row(mapOf(watchWithDiscount(100, 3, 200) to 4), 100),
            row(mapOf(watchWithDiscount(100, 3, 10) to 10), 870),
            row(
                mapOf(
                    watchWithDiscount(100, 3, 10) to 10,
                    watch(100) to 100
                ),
                870
            )
        ) { amountByWatch: Map<Watch, Int>, expected: Value ->
            // when
            val actual = discountService(amountByWatch)

            // then
            actual shouldBe expected
        }
    }
}
