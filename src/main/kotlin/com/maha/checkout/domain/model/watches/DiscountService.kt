package com.maha.checkout.domain.model.watches

import org.springframework.stereotype.Component

@Component
class DiscountService {

    private val noDiscount = 0

    operator fun invoke(amountByWatch: Map<Watch, Int>) =
        amountByWatch.map { (watch, amount) ->
            watch.discount(amount)
        }.sum()

    private fun Watch.discount(amount: Int) =
        when (this) {
            is WatchWithDiscount -> discount(amount)
            is BaseWatch -> noDiscount
        }

    private fun WatchWithDiscount.discount(amount: Int): Value {
        val discountTimesToApply = amount / discount.requiredUnitAmount
        return if (discountTimesToApply > 0) {
            discountTimesToApply * (price * discount.requiredUnitAmount - discount.batchPrice)
        } else noDiscount
    }
}
