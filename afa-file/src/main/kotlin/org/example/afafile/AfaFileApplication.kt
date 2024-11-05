package org.example.afafile

import org.example.afafile.configurations.s3.S3Properties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(S3Properties::class)
class AfaFileApplication

fun main(args: Array<String>) {
	runApplication<AfaFileApplication>(*args)
}
