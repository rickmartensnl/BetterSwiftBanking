package nl.rickmartens.betterswiftbanking.models.account

import kotlinx.datetime.*
import nl.rickmartens.betterswiftbanking.models.holders.Holder
import java.util.UUID

class Freeze(var reason: String, val executor: Holder, var until: LocalDateTime? = null) {

    val id: UUID = UUID.randomUUID()
    val since: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    var active: Boolean = true
        get() {
            return if (until != null && Clock.System.now().toLocalDateTime(TimeZone.UTC) > until!!) {
                false
            } else {
                field
            }
        }

}