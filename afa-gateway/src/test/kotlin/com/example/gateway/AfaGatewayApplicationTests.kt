package com.example.gateway

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class AfaGatewayApplicationTests {

	@Test
	fun contextLoads() {
        assert(true)
	}
}
