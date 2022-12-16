package nl.rickmartens.betterswiftbanking.exceptions

class NoBalanceException: BalanceException("The account does not have a balance.") {
}
