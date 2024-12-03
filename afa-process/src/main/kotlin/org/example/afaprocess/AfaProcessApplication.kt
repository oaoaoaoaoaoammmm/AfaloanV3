package org.example.afaprocess

import org.example.afaprocess.configurations.kafka.properties.KafkaTopicsProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties(KafkaTopicsProperties::class)
class AfaProcessApplication

fun main(args: Array<String>) {
	runApplication<AfaProcessApplication>(*args)
}
