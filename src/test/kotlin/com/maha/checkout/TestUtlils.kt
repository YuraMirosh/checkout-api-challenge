package com.maha.checkout

import com.maha.checkout.domain.model.watches.BaseWatch
import com.maha.checkout.domain.model.watches.Discount
import com.maha.checkout.domain.model.watches.Value
import com.maha.checkout.domain.model.watches.Watch
import com.maha.checkout.domain.model.watches.WatchWithDiscount
import com.maha.checkout.infrastructure.adapter.outbound.db.TestDatabaseConfiguration
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.random.Random

@ActiveProfiles("test")
@Import(TestDatabaseConfiguration::class)
@ExtendWith(value = [SpringExtension::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
annotation class WithDatabase

@WithDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
annotation class ComponentTest

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
