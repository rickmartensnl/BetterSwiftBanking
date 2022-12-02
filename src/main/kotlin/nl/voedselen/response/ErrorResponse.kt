package nl.voedselen.response

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class ErrorResponse {
	
	val status: String
	val code: Int
	val message: String
	@Transient var rawStatus: HttpStatusCode = HttpStatusCode.InternalServerError
	
	constructor(status: HttpStatusCode, opcode: Opcodes = Opcodes.GENERAL_ERROR) {
		this.rawStatus = status
		this.status = "${status.value}: ${status.description}"
		this.code = opcode.code
		message = opcode.message
	}
	
	suspend fun respondToCall(call: ApplicationCall) {
		call.respond(rawStatus, this)
	}
	
}
