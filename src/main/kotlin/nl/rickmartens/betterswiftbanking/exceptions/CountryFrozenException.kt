package nl.rickmartens.betterswiftbanking.exceptions

class CountryFrozenException: FrozenException("The country of the target or issuer has been frozen.") {
}