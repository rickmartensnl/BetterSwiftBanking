package nl.rickmartens.betterswiftbanking.models.money

enum class Currency(val valueToUsd: Double, val fullName: String) {

    USD(1.0, "United States Dollar"),
    EUR(1.05, "Euro")

}
