package nl.rickmartens.betterswiftbanking.models.money

import nl.rickmartens.betterswiftbanking.models.holders.Country
import java.util.UUID

class Cash(amount: Double, override val currency: Currency, val country: Country) : Money {

    val id: UUID = UUID.randomUUID()

    override var amount: Double = amount
        set(value) = Unit

}
