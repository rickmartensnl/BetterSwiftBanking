package nl.rickmartens.betterswiftbanking.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*
import nl.rickmartens.betterswiftbanking.service.ExampleService

fun Application.registerApiRoutes(exampleService: ExampleService) {
    
    routing {
        route("/api/v1") {
            route("/example") {
        
                get("{id}") { exampleService.getExampleById(call) }
        
            }
        }
    }
    
}
