package nl.voedselen.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import nl.voedselen.response.ErrorResponse
import nl.voedselen.service.ExampleService

// TODO: Rename to service.
fun Application.registerExampleRoutes(exampleService: ExampleService) {
    
    routing {
        get("/health") {
            ErrorResponse(HttpStatusCode.OK).respondToCall(call)
        }
    
        // Example Authenticated Route
        authenticate {
            get("/hello") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
            }
        }
        
        route("/api/v1") {
            // TODO: Rename to service.
            route("/example") {
        
                get("{id}") { exampleService.getExampleById(call) }
        
            }
        }
    }
    
}
