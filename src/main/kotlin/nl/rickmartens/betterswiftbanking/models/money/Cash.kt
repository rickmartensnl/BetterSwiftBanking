package nl.rickmartens.betterswiftbanking.models.money

import nl.rickmartens.betterswiftbanking.models.holders.Country
import java.util.UUID

class Cash(override val amount: Double, override val currency: Currency, val country: Country) : Money {

    val id: UUID = UUID.randomUUID()

}