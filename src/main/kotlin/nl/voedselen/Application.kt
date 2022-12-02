package nl.voedselen

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import nl.voedselen.plugins.*
import nl.voedselen.routes.registerExampleRoutes
import nl.voedselen.security.JwtAuthentication
import nl.voedselen.service.ExampleServiceImpl
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val mongoUrl = System.getenv("MONGO_URL")
    val dbName = "voedselen"
    val db = KMongo.createClient(mongoUrl).coroutine.getDatabase(dbName)
    val jwt = JwtAuthentication(
        "",
        this.environment.config.property("jwt.issuer").getString(),
        this.environment.config.property("jwt.audience").getString(),
        this.environment.config.property("jwt.realm").getString()
    )
    
    configureSerialization()
    configureSecurity(jwt)
    configureHTTP()
    configureMonitoring()
    configureStatusPages()
    
    registerExampleRoutes(ExampleServiceImpl(db))
}
