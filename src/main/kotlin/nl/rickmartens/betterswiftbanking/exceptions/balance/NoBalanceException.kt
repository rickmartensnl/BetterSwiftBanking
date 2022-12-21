package nl.rickmartens.betterswiftbanking.exceptions.balance

class NoBalanceException: BalanceException("The account does not have a balance.") {
}
