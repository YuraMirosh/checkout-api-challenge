package com.maha.checkout

import com.maha.checkout.domain.model.watches.BaseWatch
import com.maha.checkout.domain.model.watches.Discount
import com.maha.checkout.domain.model.watches.Value
import com.maha.checkout.domain.model.watches.Watch
import com.maha.checkout.domain.model.watches.WatchWithDiscount
import kotlin.random.Random

fun watch(price: Value = Random.nextInt()): Watch = BaseWatch(
    String(Random.nextBytes(10)),
    price
)

fun watchWithDiscount(price: Value, requiredUnitAmount: Int, batchPrice: Int) = WatchWithDiscount(
    String(Random.nextBytes(10)),
    price,
    Discount(
        requiredUnitAmount,
        batchPrice
    )
)
