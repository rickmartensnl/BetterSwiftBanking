package nl.rickmartens.betterswiftbanking.utils

import nl.rickmartens.betterswiftbanking.models.money.Currency

fun exchangeCurrency(amount: Double, currency: Currency, newCurrency: Currency): Double {
    var newAmount = amount

    if (currency != newCurrency) {
        var usd: Double = amount

        if (currency != Currency.USD) {
            usd = currency.valueToUsd * amount
        }

        newAmount = usd / newCurrency.valueToUsd
    }

    return newAmount
}