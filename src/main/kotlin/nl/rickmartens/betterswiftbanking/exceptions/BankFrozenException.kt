package nl.rickmartens.betterswiftbanking.exceptions

class BankFrozenException: FrozenException("The bank of the target or issuer has been frozen.") {
}