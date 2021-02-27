package com.maha.checkout.infrastructure.adapter.outbound.db

import com.maha.checkout.domain.model.watches.BaseWatch
import com.maha.checkout.domain.model.watches.Discount
import com.maha.checkout.domain.model.watches.Value
import com.maha.checkout.domain.model.watches.Watch
import com.maha.checkout.domain.model.watches.WatchWithDiscount
import com.maha.checkout.domain.model.watches.WatchesPort
import com.maha.checkout.infrastructure.adapter.inbound.http.NotFoundException
import io.r2dbc.spi.Row
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.function.Function

@Repository
class H2WatchesPort(
    private val databaseClient: DatabaseClient
) : WatchesPort {

    private val watchMapper = WatchMapper()

    @Transactional
    override suspend fun findByIds(ids: Set<String>): List<Watch> =
        if (ids.isEmpty()) emptyList()
        else
            databaseClient.sql("select * from watch where id in (:ids)")
                .bind("ids", ids)
                .map(watchMapper::apply)
                .all()
                .collectList()
                .awaitSingle()
                .validateAllIdsPresent(ids)

    private fun List<Watch>.validateAllIdsPresent(ids: Set<String>) =
        also {
            if (it.size != ids.size) throw NotFoundException(
                "Failed to find watches in catalog by ids: '${ids - it.map { it.id }}'"
            )
        }
}

private class WatchMapper : Function<Row, Watch> {

    override fun apply(row: Row): Watch =
        row["discount_unit_amount"].let {
            when (it) {
                null -> BaseWatch(
                    id = row["id"] as String,
                    price = row["unit_price"] as Value
                )
                else -> WatchWithDiscount(
                    id = row["id"] as String,
                    price = row["unit_price"] as Value,
                    discount = Discount(
                        requiredUnitAmount = it as Int,
                        batchPrice = row["discount_batch_price"] as Int
                    )
                )
            }
        }
}
