package nl.voedselen.service

import io.ktor.server.application.*
import nl.voedselen.models.User
import org.litote.kmongo.coroutine.CoroutineDatabase

// TODO: Rename to service.
class ExampleServiceImpl(private val db: CoroutineDatabase) : ExampleService {
	
	private val collection = db.getCollection<User>()
	
	override suspend fun getExampleById(call: ApplicationCall) {
		TODO("Not yet implemented")
	}
	
	override suspend fun getExampleByEmail(call: ApplicationCall) {
		TODO("Not yet implemented")
	}
	
	override suspend fun getExamples(call: ApplicationCall) {
		TODO("Not yet implemented")
	}
	
	override suspend fun updateExample(call: ApplicationCall) {
		TODO("Not yet implemented")
	}
	
	override suspend fun deleteExample(call: ApplicationCall) {
		TODO("Not yet implemented")
	}
	
}