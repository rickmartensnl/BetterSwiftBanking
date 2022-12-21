package nl.rickmartens.betterswiftbanking.models.account

import kotlinx.datetime.LocalDateTime
import nl.rickmartens.betterswiftbanking.exceptions.frozen.AccountFrozenException
import nl.rickmartens.betterswiftbanking.exceptions.balance.InvalidBalanceException
import nl.rickmartens.betterswiftbanking.exceptions.balance.NoBalanceException
import nl.rickmartens.betterswiftbanking.models.holders.Bank
import nl.rickmartens.betterswiftbanking.models.holders.Country
import nl.rickmartens.betterswiftbanking.models.holders.Holder
import nl.rickmartens.betterswiftbanking.models.money.Cash
import nl.rickmartens.betterswiftbanking.models.money.Currency
import nl.rickmartens.betterswiftbanking.models.money.Digital
import nl.rickmartens.betterswiftbanking.models.money.Money
import nl.rickmartens.betterswiftbanking.utils.exchangeCurrency
import java.util.UUID

fun main() {
    val nl = Country("NL", "NL", Currency.EUR)
    val us = Country("US", "US", Currency.USD)
    val bank = Bank("ING", "INGB", nl)
    val bank2 = Bank("ING", "INGB", us)
    val account = Account(1, bank, "Me", Digital(100.0, bank.country.currency))
    val account2 = Account(2, bank2, "Me2", Digital(0.0, bank2.country.currency))

    val cash = account.withdraw(10.0, us)
    println("Account ${account.money.currency.name}: ${account.money.amount}")
    println("Cash money ${cash.currency.name}: ${cash.amount}")

    account2.deposit(cash)
    println("Account2 ${account2.money.currency.name}: ${account2.money.amount}")

    val cash2 = account2.withdraw(10.5, us)
    println("Account2 ${account2.money.currency.name}: ${account2.money.amount}")
    println("Cash money ${cash2.currency.name}: ${cash2.amount}")

    account.deposit(cash2)
    println("Account ${account.money.currency.name}: ${account.money.amount}")
}

class Account(val id: Long, val bank: Bank, var owner: String, val money: Digital) {

    val frozenHistory: Map<UUID, Freeze> = emptyMap()
    var transactionHistory: List<Transaction> = listOf()
    val iban: IBAN
        get() {
            return IBAN(this)
        }
    val frozen: Boolean
        get() {
            var result = false
            frozenHistory.forEach {
                if (it.value.active) {
                    result = true
                }
            }

            return result
        }

    fun transfer(target: IBAN, amount: Double) {
        TODO("Implement transfer code")
    }

    fun transfer(account: Account, amount: Double, description: String = "Money transfer.", currency: Currency = money.currency) {
        val digital = Digital(amount, currency)
        println("Transfer has currency: ${currency.name}")
        val transaction = Transaction(this, account, digital, description)
        removeMoney(digital)
        transactionHistory += transaction
        account.addMoney(digital)
        account.transactionHistory += transaction
    }

    private fun addMoney(money: Money) {
        if (frozen) {
            throw AccountFrozenException()
        }

        val amount = exchangeCurrency(money.amount, money.currency, this.money.currency)

        if (amount <= 0) {
            throw InvalidBalanceException()
        }

        this.money.amount += amount
    }

    private fun removeMoney(money: Money) {
        if (frozen) {
            throw AccountFrozenException()
        }

        val amount = exchangeCurrency(money.amount, money.currency, this.money.currency)

        if (amount <= 0) {
            throw InvalidBalanceException()
        }

        if (this.money.amount < amount) {
            throw NoBalanceException()
        }

        this.money.amount -= amount
    }

    fun withdraw(amount: Double, country: Country = bank.country): Cash {
        val newAmount = exchangeCurrency(amount, money.currency, country.currency)
        val cash = Cash(newAmount, country.currency, country)

        val transaction = Transaction(this, null, cash, "Withdrawal of cash.")
        removeMoney(cash)
        transactionHistory += transaction

        return cash
    }

    fun deposit(cash: Cash) {
        val transaction = Transaction(null, this, cash, "Deposit of cash.")
        addMoney(cash)
        transactionHistory += transaction
    }

    fun freeze(reason: String, executor: Holder, until: LocalDateTime? = null) {
        TODO("Implement freeze code")
    }

    fun unfreeze() {
        TODO("Implement freeze code")
    }

    fun unfreezeById(freezeId: UUID) {
        TODO("Implement freeze code")
    }

}
