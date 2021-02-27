package com.maha.checkout.domain.model.watches

import org.springframework.stereotype.Component

@Component
class TotalPriceService(
    private val discountService: DiscountService
) {

    operator fun invoke(amountByWatch: Map<Watch, Int>): Value {
        val initialValue = calculateInitialPrice(amountByWatch)
        val discountValue = discountService(amountByWatch)
        return initialValue.applyDiscount(discountValue)
    }

    private fun calculateInitialPrice(amountByWatch: Map<Watch, Int>) =
        amountByWatch.map { it.key.price * it.value }.sum()

    private fun Value.applyDiscount(discount: Value) =
        this - discount
}
