package com.maha.checkout.infrastructure.adapter.outbound.db

import com.maha.checkout.domain.model.watches.Watch
import com.maha.checkout.domain.model.watches.WatchesPort
import org.springframework.stereotype.Repository

@Repository
class H2WatchesPort : WatchesPort {

    override suspend fun findByIds(ids: Set<String>): List<Watch> =
        TODO("Not implemented")
}
