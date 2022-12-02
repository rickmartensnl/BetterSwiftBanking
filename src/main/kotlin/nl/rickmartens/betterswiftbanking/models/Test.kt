package nl.rickmartens.betterswiftbanking.models

import kotlinx.datetime.*
import nl.rickmartens.betterswiftbanking.models.account.Freeze
import nl.rickmartens.betterswiftbanking.models.holders.Bank
import nl.rickmartens.betterswiftbanking.models.holders.Country
import nl.rickmartens.betterswiftbanking.models.money.Cash
import nl.rickmartens.betterswiftbanking.models.money.Currency
import kotlin.time.Duration

fun main() {
    val country = Country("Netherlands, the", "NL", Currency.USD)

    country.name = "count"
    country.code = "cc"

    println(country.name)
    println(country.code)
    println()

    val bank = Bank("ING Bank", "INGB", country)

    println(bank.name)
    println(bank.code)
    println()

    val freeze = Freeze("No", bank, null)

    println(freeze.active)
    println()

    freeze.until = Clock.System.now().plus(Duration.parse("1s")).toLocalDateTime(TimeZone.UTC)

    println(freeze.active)

    Thread.sleep(1000)

    println(freeze.active)
    println()

    freeze.until = Clock.System.now().plus(Duration.parse("5s")).toLocalDateTime(TimeZone.UTC)
    println(freeze.active)
    freeze.active = false
    println(freeze.active)

    Thread.sleep(5000)

    println(freeze.active)
    println()

    val cash = Cash(10.0, Currency.USD, country)

    println(cash.amount)
    cash.amount = 1000.0
    println(cash.amount)
}
