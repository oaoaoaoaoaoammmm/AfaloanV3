package org.example.afaprocess.services.clients

import feign.Headers
import org.example.afaprocess.configurations.feign.FeignConfiguration
import org.example.afaprocess.services.clients.dtos.MicroloanDto
import org.example.afaprocess.services.clients.dtos.OrderDto
import org.example.afaprocess.services.clients.dtos.ProfileDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.util.*

@FeignClient(value = "\${spring.cloud.openfeign.afa-order.name}", configuration = [FeignConfiguration::class])
interface AfaOrderClient {

    @Headers("Content-Type: application/json")
    @RequestMapping(method = [RequestMethod.GET], value = ["/profiles/{id}"])
    fun findProfile(@PathVariable id: UUID): ProfileDto

    @Headers("Content-Type: application/json")
    @RequestMapping(method = [RequestMethod.GET], value = ["/bids/{id}"])
    fun findOrder(@PathVariable id: UUID): OrderDto

    @Headers("Content-Type: application/json")
    @RequestMapping(method = [RequestMethod.GET], value = ["/microloans/{id}"])
    fun findMicroloan(@PathVariable id: UUID): MicroloanDto
}