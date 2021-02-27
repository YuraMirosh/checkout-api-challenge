package com.maha.checkout.infrastructure.adapter.inbound.http

import com.maha.checkout.application.TotalPriceUseCase

class CheckoutController(
    private val totalPriceUseCase: TotalPriceUseCase
) {

    suspend fun checkout(ids: List<String>) =
        totalPriceUseCase(ids)
            .let(::CheckoutResponse)
}

data class CheckoutResponse(
    val price: Int
)
