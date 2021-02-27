package com.maha.checkout.infrastructure.adapter.inbound.http

import com.maha.checkout.application.TotalPriceUseCase
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

const val CHECKOUT_PATH = "/checkout"

@RestController
class CheckoutController(
    private val totalPriceUseCase: TotalPriceUseCase
) {

    @PostMapping(
        value = [CHECKOUT_PATH],
        consumes = [APPLICATION_JSON_VALUE],
        produces = [APPLICATION_JSON_VALUE]
    )
    suspend fun checkout(@RequestBody ids: List<String>) =
        totalPriceUseCase(ids)
            .let(::CheckoutResponse)
}

data class CheckoutResponse(
    val price: Int
)
