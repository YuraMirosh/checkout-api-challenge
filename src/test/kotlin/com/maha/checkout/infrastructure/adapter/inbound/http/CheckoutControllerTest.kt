package com.maha.checkout.infrastructure.adapter.inbound.http

import com.maha.checkout.application.TotalPriceUseCase
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

internal class CheckoutControllerTest {

    private val useCase = mockk<TotalPriceUseCase>()

    private var client = WebTestClient
        .bindToController(CheckoutController(useCase))
        .controllerAdvice(ExceptionHandler())
        .build()

    @Test
    fun `should be INTERNAL_SERVER_ERROR when useCase throws exception`() {
        // given
        coEvery { useCase(any()) } throws RuntimeException()

        // when
        val response = execute()

        // then
        response
            .expectStatus().isEqualTo(500)
            .expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
    }

    @Test
    fun `should be NOT_FOUND when useCase throws NOT_FOUND exception`() {
        // given
        coEvery { useCase(any()) } throws NotFoundException("")

        // when
        val response = execute()

        // then
        response
            .expectStatus().isNotFound
            .expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
    }

    @Test
    fun `should return expected Price response`() {
        // given
        val request = """["001","002","001","004","003"]"""
        val expected = 5
        coEvery { useCase(listOf("001", "002", "001", "004", "003")) } returns expected

        // when
        val response = execute(request)

        // then
        response
            .expectStatus().isOk
            .expectHeader().contentType(APPLICATION_JSON_VALUE)
            .expectBody().json("""{ "price": $expected }""")
    }

    private fun execute(request: String = "[]") =
        client.post()
            .uri(CHECKOUT_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(request))
            .exchange()
}
