package com.maha.checkout.component

import com.maha.checkout.ComponentTest
import com.maha.checkout.infrastructure.adapter.inbound.http.CHECKOUT_PATH
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

@ComponentTest
class CheckoutComponentTest {

    @Autowired
    private lateinit var context: ApplicationContext

    private val client by lazy {
        WebTestClient
            .bindToApplicationContext(context)
            .configureClient()
            .build()
    }

    @Test
    fun `should return expected price on success`() {
        // given
        val request = """["001","002","001","004","003"]"""
        val expectedPrice = 360

        // when
        val response = execute(request)

        // then
        response verifyOkResponse expectedPrice
    }

    @Test
    fun `should return 0 for empty list of ids`() {
        // given
        val request = "[]"
        val expectedPrice = 0

        // when
        val response = execute(request)

        // then
        response verifyOkResponse expectedPrice
    }

    private fun execute(request: String) =
        client.post()
            .uri(CHECKOUT_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(request))
            .exchange()

    private infix fun WebTestClient.ResponseSpec.verifyOkResponse(expected: Int) =
        expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
            .expectBody().json("""{ "price": $expected }""")
}
