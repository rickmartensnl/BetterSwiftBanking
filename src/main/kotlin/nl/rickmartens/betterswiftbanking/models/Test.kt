package nl.rickmartens.betterswiftbanking.models

import nl.rickmartens.betterswiftbanking.models.holders.Bank
import nl.rickmartens.betterswiftbanking.models.holders.Country
import nl.rickmartens.betterswiftbanking.models.money.Currency

fun main() {
    val country = Country("Netherlands, the", "NL", Currency.USD)

    country.name = "count"
    country.code = "cc"

    println(country.name)
    println(country.code)

    val bank = Bank("ING Bank", "INGB", country)

    println(bank.name)
    println(bank.code)
}
