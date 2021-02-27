package com.maha.checkout.domain.model.watches

interface WatchesPort {

    suspend fun findByIds(ids: Set<String>): List<Watch>
}
