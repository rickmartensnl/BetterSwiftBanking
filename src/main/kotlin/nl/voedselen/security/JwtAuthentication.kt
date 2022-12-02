package nl.voedselen.security

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.JwkProviderBuilder
import java.util.concurrent.TimeUnit

class JwtAuthentication(val privateKey: String, val issuer: String, val audience: String, val realm: String) {
	
	val jwkProvider: JwkProvider = JwkProviderBuilder(issuer)
		.cached(10, 24, TimeUnit.HOURS)
		.rateLimited(10, 1, TimeUnit.MINUTES)
		.build()
	
}