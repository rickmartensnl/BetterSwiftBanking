package nl.voedselen.models

import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@Serializable
data class User(
    @Transient @Contextual val _id: Id<User> = newId(),
    var id: String = "",
    var email: String,
    val createdAt: Instant,
    var updatedAt: Instant,
    @Transient var password: String? = "",
    var role: Role = Role.USER,
    var profile: Profile,
) {
    
    init {
        id = _id.toString()
    }
    
}

@Serializable
data class Profile(var firstName: String, var lastName: String)

enum class Role {
    USER,
    ADMIN
}
