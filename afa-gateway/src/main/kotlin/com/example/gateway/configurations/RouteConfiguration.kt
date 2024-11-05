package com.example.gateway.configurations

import com.example.gateway.filters.AuthenticationFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI

@Configuration
class RouteConfiguration {

    @Bean
    fun routeLocator(
        route: RouteLocatorBuilder,
        props: ServiceUrlsProperties,
        authFilter: AuthenticationFilter,
        @Value("\${server.api.prefix}") apiPrefix: String,
    ): RouteLocator {
        return route.routes {
            route(id = "${props.afaUser}-route-auth") {
                uri("lb://${props.afaUser}")
                path("$apiPrefix/auth/**")
                filters {
                    stripPrefix(2)
                    circuitBreaker {
                        it.name = "${props.afaUser}-circuit-breaker"
                        it.fallbackUri = URI.create("forward:/fallback")
                    }
                }
            }
            route(id = "${props.afaUser}-route-users") {
                uri("lb://${props.afaUser}")
                path("$apiPrefix/users/**")
                filters {
                    stripPrefix(2)
                    circuitBreaker {
                        it.name = "${props.afaUser}-circuit-breaker"
                        it.fallbackUri = URI.create("forward:/fallback")
                    }
                    filter(authFilter.apply(AuthenticationFilter.Config))
                }
            }
            route(id = "${props.afaOrder}-route") {
                uri("lb://${props.afaOrder}")
                path(
                    "$apiPrefix/bids/**",
                    "$apiPrefix/boiling-points/**",
                    "$apiPrefix/microloans/**",
                    "$apiPrefix/profiles/**"
                )
                filters {
                    stripPrefix(2)
                    circuitBreaker {
                        it.name = "${props.afaOrder}-circuit-breaker"
                        it.fallbackUri = URI.create("forward:/fallback")
                    }
                    filter(authFilter.apply(AuthenticationFilter.Config))
                }
            }
            route(id = "${props.afaProcess}-route") {
                uri("lb://${props.afaProcess}")
                path("$apiPrefix/processes/**")
                filters {
                    stripPrefix(2)
                    circuitBreaker {
                        it.name = "${props.afaProcess}-circuit-breaker"
                        it.fallbackUri = URI.create("forward:/fallback")
                    }
                    filter(authFilter.apply(AuthenticationFilter.Config))
                }
            }
        }
    }
}