package org.example.afaprocess.configurations.feign

import feign.codec.Decoder
import feign.codec.Encoder
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.example.afaprocess.utils.mapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfiguration {

    @Bean
    fun decoder(): Decoder {
        return JacksonDecoder(mapper)
    }

    @Bean
    fun encoder(): Encoder {
        return JacksonEncoder(mapper)
    }
}