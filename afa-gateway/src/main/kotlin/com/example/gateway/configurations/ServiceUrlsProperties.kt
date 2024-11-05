package com.example.gateway.configurations

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "spring.services.urls")
data class ServiceUrlsProperties@ConstructorBinding constructor(
    val afaUser: String,
    val afaOrder: String,
    val afaProcess: String
)
