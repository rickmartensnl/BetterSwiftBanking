package nl.rickmartens.betterswiftbanking

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import nl.rickmartens.betterswiftbanking.plugins.configureHTTP
import nl.rickmartens.betterswiftbanking.plugins.configureMonitoring
import nl.rickmartens.betterswiftbanking.plugins.configureSerialization
import nl.rickmartens.betterswiftbanking.plugins.configureStatusPages
import nl.rickmartens.betterswiftbanking.routes.registerApiRoutes
import nl.rickmartens.betterswiftbanking.service.ExampleServiceImpl

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureHTTP()
    configureMonitoring()
    configureStatusPages()
    
    registerApiRoutes(ExampleServiceImpl())
}
