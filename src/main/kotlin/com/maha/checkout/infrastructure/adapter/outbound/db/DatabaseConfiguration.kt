package com.maha.checkout.infrastructure.adapter.outbound.db

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@Profile("!test")
@Configuration
class DatabaseConfiguration {

    @Bean
    fun initializer(connectionFactory: ConnectionFactory) =
        ConnectionFactoryInitializer()
            .also { it.setConnectionFactory(connectionFactory) }
            .also {
                it.setDatabasePopulator(
                    ResourceDatabasePopulator(
                        ClassPathResource("db/schema.sql"), ClassPathResource("db/data.sql")
                    )
                )
            }
}
