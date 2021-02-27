package com.maha.checkout.application

import com.maha.checkout.domain.model.watches.TotalPriceService
import com.maha.checkout.domain.model.watches.Value
import com.maha.checkout.domain.model.watches.Watch
import com.maha.checkout.domain.model.watches.WatchesPort
import org.springframework.stereotype.Component

@Component
class TotalPriceUseCase(
    private val watchesPort: WatchesPort,
    private val totalPriceService: TotalPriceService
) {

    suspend operator fun invoke(ids: List<String>): Value =
        ids.toSet()
            .let { watchesPort.findByIds(it) }
            .let { groupAmountByWatch(ids, it) }
            .let { totalPriceService(it) }

    private fun groupAmountByWatch(ids: List<String>, watches: List<Watch>): Map<Watch, Int> {
        val amountByWatch = mutableMapOf<Watch, Int>()
        val amountById = ids.groupingBy { it }.eachCount()
        watches.forEach { amountByWatch[it] = amountById[it.id]!! }
        return amountByWatch
    }
}
