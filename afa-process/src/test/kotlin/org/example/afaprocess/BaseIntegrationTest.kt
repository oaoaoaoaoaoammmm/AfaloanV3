package org.example.afaprocess

import org.example.afaprocess.configurations.PostgresAutoConfiguration
import org.example.afaprocess.utils.createAuthentication
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockAuthentication
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [PostgresAutoConfiguration::class]
)
@ActiveProfiles("test")
abstract class BaseIntegrationTest {

    protected lateinit var client: WebTestClient

    @BeforeEach
    fun setUp(context: ApplicationContext) {
        client = WebTestClient.bindToApplicationContext(context)
            .apply(springSecurity())
            .configureClient()
            .build()
            .mutateWith(mockAuthentication(createAuthentication()))
    }
}