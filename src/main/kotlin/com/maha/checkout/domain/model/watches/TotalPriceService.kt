package com.maha.checkout.domain.model.watches

class TotalPriceService(
    private val discountService: DiscountService
) {

    operator fun invoke(amountByWatch: Map<Watch, Int>): Value =
        TODO("Not implemented")
}
