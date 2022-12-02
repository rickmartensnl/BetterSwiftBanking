package nl.rickmartens.betterswiftbanking.models.account

import nl.rickmartens.betterswiftbanking.models.money.Money
import java.util.*

class Transaction(val from: Account, val to: Account?, val money: Money, val description: String) {

    val id: UUID = UUID.randomUUID()

}