package com.kgoro.sangoma_link

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity

@SpringBootApplication
@EnableMethodSecurity
class SangomaLinkApplication

fun main(args: Array<String>) {
	runApplication<SangomaLinkApplication>(*args)
}
