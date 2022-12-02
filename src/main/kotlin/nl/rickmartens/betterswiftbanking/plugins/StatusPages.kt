package nl.rickmartens.betterswiftbanking.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import nl.rickmartens.betterswiftbanking.response.ErrorResponse
import nl.rickmartens.betterswiftbanking.response.Opcodes

fun Application.configureStatusPages() {
	
	install(StatusPages) {
		exception<RequestValidationException> { call, cause ->
			val code: Opcodes = Opcodes.fromCode(cause.reasons.first().toInt())
			ErrorResponse(HttpStatusCode.BadRequest, code).respondToCall(call)
		}
		
		exception<BadRequestException> { call, _ ->
			ErrorResponse(HttpStatusCode.BadRequest).respondToCall(call)
		}
		
		status(HttpStatusCode.NotFound) { call, status ->
			ErrorResponse(status).respondToCall(call)
		}
		
		status(HttpStatusCode.MethodNotAllowed) { call, status ->
			ErrorResponse(status).respondToCall(call)
		}
		
		status(HttpStatusCode.UnsupportedMediaType) { call, status ->
			ErrorResponse(status).respondToCall(call)
		}
		
		exception<NotImplementedError> { call, _ ->
			ErrorResponse(HttpStatusCode.NotImplemented).respondToCall(call)
		}
		
		exception<Throwable> { call, cause ->
			cause.printStackTrace()
			println(cause.javaClass.name)
			ErrorResponse(HttpStatusCode.InternalServerError).respondToCall(call)
		}
	}
	
}
