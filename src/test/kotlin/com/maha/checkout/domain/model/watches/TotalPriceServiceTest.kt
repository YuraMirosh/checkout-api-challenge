package com.maha.checkout.domain.model.watches

import com.maha.checkout.watch
import io.kotlintest.data.suspend.forall
import io.kotlintest.shouldBe
import io.kotlintest.tables.row
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class TotalPriceServiceTest {

    @MockK
    lateinit var discountService: DiscountService

    @InjectMockKs
    private lateinit var totalPriceService: TotalPriceService

    @Test
    fun `should return expected total value`() = runBlocking {
        forall(
            row(emptyMap(), 0, 0),
            row(mapOf(watch(100) to 10), 0, 1000),
            row(mapOf(watch(100) to 10), 100, 900),
            row(
                mapOf(
                    watch(10) to 1,
                    watch(20) to 2
                ),
                5,
                45
            )
        ) { amountByWatch, discount, expected ->
            // given
            every { discountService(amountByWatch) } returns discount

            // when
            val actual = totalPriceService(amountByWatch)

            // then
            actual shouldBe expected
        }
    }
}
