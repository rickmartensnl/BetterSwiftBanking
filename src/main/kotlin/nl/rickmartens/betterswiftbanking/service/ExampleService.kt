package nl.rickmartens.betterswiftbanking.service

import io.ktor.server.application.*

// TODO: Rename to service.
interface ExampleService {
    
    // TODO: Rename to service.
    suspend fun getExamples(call: ApplicationCall)
    
    // TODO: Rename to service.
    suspend fun getExampleById(call: ApplicationCall)
    
    // TODO: Rename to service.
    suspend fun getExampleByEmail(call: ApplicationCall)
    
    // TODO: Rename to service.
    suspend fun updateExample(call: ApplicationCall)
    
    // TODO: Rename to service.
    suspend fun deleteExample(call: ApplicationCall)
    
}