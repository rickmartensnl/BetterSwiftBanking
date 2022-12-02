package nl.voedselen.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.http.*
import io.ktor.server.application.*
import nl.voedselen.response.ErrorResponse
import nl.voedselen.response.Opcodes
import nl.voedselen.security.JwtAuthentication

fun Application.configureSecurity(jwt: JwtAuthentication) {
    
    authentication {
        jwt {
            realm = jwt.realm
            
            verifier(jwt.jwkProvider, jwt.issuer) {
                acceptLeeway(3)
            }
            
            validate { credential ->
                // TODO: Set the user as a variable.
                // TODO: Use by `val principal = call.principal<JWTPrincipal>()`
                if (credential.payload.subject != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            
            challenge { _, _ ->
                ErrorResponse(HttpStatusCode.Unauthorized, Opcodes.TOKEN_INVALID_OR_EXPIRED).respondToCall(call)
            }
        }
    }
    
}
