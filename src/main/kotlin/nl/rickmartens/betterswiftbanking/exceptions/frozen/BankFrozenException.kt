package nl.rickmartens.betterswiftbanking.exceptions.frozen

class BankFrozenException: FrozenException("The bank of the target or issuer has been frozen.") {
}