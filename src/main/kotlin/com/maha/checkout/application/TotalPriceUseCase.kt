package com.maha.checkout.application

import com.maha.checkout.domain.model.watches.TotalPriceService
import com.maha.checkout.domain.model.watches.Value
import com.maha.checkout.domain.model.watches.WatchesPort

class TotalPriceUseCase(
    private val watchesPort: WatchesPort,
    private val totalPriceService: TotalPriceService
) {

    suspend operator fun invoke(ids: List<String>): Value =
        TODO("Not implemented")
}
