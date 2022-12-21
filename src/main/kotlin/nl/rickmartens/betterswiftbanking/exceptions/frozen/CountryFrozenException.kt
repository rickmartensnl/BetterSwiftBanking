package nl.rickmartens.betterswiftbanking.exceptions.frozen

class CountryFrozenException: FrozenException("The country of the target or issuer has been frozen.") {
}