package com.maha.checkout.domain.model.watches

sealed class Watch(
    open val id: String,
    open val price: Value
)

data class BaseWatch(
    override val id: String,
    override val price: Value
) : Watch(id, price)

data class WatchWithDiscount(
    override val id: String,
    override val price: Value,
    val discount: Discount
) : Watch(id, price)

data class Discount(
    val requiredUnitAmount: Int,
    val batchPrice: Int
)

typealias Value = Int
