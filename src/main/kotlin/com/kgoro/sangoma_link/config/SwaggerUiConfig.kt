package com.kgoro.sangoma_link.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "Sangoma Link API",
        version = "1.0.0",
        description = "This is the Sangoma Link backend API"
    ),
    servers = [
        Server(url = "http://localhost:8080", description = "Local Development Server")
    ],
    security = [
        SecurityRequirement(name = "BearerAuth")
    ]
)
@SecurityScheme(
    name = "BearerAuth",
    description = "Enter your JWT token to authenticate requests.",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    `in` = SecuritySchemeIn.HEADER
)
@SecurityScheme(
    name = "BasicAuth",
    description = "Enter your username and password to authenticate.",
    type = SecuritySchemeType.HTTP,
    scheme = "basic"
)
// Container annotation used to group multiple security schemes on a single class
//@io.swagger.v3.oas.annotations.security.SecuritySchemes(
//    value = [
//        SecurityScheme(name = "BearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", `in` = SecuritySchemeIn.HEADER),
//        SecurityScheme(name = "BasicAuth", type = SecuritySchemeType.HTTP, scheme = "basic", description = "Enter username and password.")
//    ]
//)
class SwaggerUiConfig