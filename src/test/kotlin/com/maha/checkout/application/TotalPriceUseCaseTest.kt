package com.maha.checkout.application

import com.maha.checkout.domain.model.watches.BaseWatch
import com.maha.checkout.domain.model.watches.TotalPriceService
import com.maha.checkout.domain.model.watches.WatchesPort
import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class TotalPriceUseCaseTest {

    @MockK
    lateinit var watchesPort: WatchesPort

    @MockK
    lateinit var totalPriceService: TotalPriceService

    @InjectMockKs
    private lateinit var totalPriceUseCase: TotalPriceUseCase

    @Test
    fun `should call watchesPort with unique ids`() = runBlocking {
        // given
        val ids = listOf("1", "1")
        coEvery { watchesPort.findByIds(any()) } returns emptyList()
        coEvery { totalPriceService(any()) } returns 0

        // when
        totalPriceUseCase(ids)

        // then
        coVerify { watchesPort.findByIds(setOf("1")) }
    }

    @Test
    fun `should call totalPriceService with expected amountByWatch`() = runBlocking {
        // given
        val ids = listOf("1", "2", "1")
        val watches = ids.toSet().mapIndexed { index, id -> BaseWatch(id, index) }
        coEvery { watchesPort.findByIds(any()) } returns watches
        coEvery { totalPriceService(any()) } returns 0

        // when
        totalPriceUseCase(ids)

        // then
        coVerify { totalPriceService(mapOf(watches[0] to 2, watches[1] to 1)) }
    }

    @Test
    fun `should return result of totalPriceService`() = runBlocking {
        // given
        val expected = 0
        val ids = listOf("1", "2", "1")
        coEvery { watchesPort.findByIds(any()) } returns emptyList()
        coEvery { totalPriceService(any()) } returns expected

        // when
        val actual = totalPriceUseCase(ids)

        // then
        actual shouldBe expected
    }
}
