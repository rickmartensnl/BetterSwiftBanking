package nl.rickmartens.betterswiftbanking.exceptions.frozen

class AccountFrozenException: FrozenException("The account of the target or issuer is frozen for any transfers of money.") {
}