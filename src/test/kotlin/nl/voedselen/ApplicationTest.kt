package nl.voedselen

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.auth.*
import io.ktor.util.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.callloging.*
import org.slf4j.event.*
import io.ktor.server.request.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.test.*
import io.ktor.server.testing.*
import nl.voedselen.plugins.*
import nl.voedselen.routes.registerExampleRoutes
import nl.voedselen.security.JwtAuthentication
import nl.voedselen.service.ExampleServiceImpl
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
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
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }
}