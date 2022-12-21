package nl.rickmartens.betterswiftbanking.models.account

import kotlinx.datetime.LocalDateTime
import nl.rickmartens.betterswiftbanking.exceptions.frozen.AccountFrozenException
import nl.rickmartens.betterswiftbanking.exceptions.balance.InvalidBalanceException
import nl.rickmartens.betterswiftbanking.exceptions.balance.NoBalanceException
import nl.rickmartens.betterswiftbanking.exceptions.frozen.BankFrozenException
import nl.rickmartens.betterswiftbanking.exceptions.frozen.CountryFrozenException
import nl.rickmartens.betterswiftbanking.exceptions.frozen.FrozenException
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
    if (cash != null) {
        println("Account ${account.money.currency.name}: ${account.money.amount}")
        println("Cash money ${cash.currency.name}: ${cash.amount}")

        account2.deposit(cash)
        println("Account2 ${account2.money.currency.name}: ${account2.money.amount}")
    } else {
        println("Cannot do cash checks")
    }

    val cash2 = account2.withdraw(10.5, us)
    if (cash2 != null) {
        println("Account2 ${account2.money.currency.name}: ${account2.money.amount}")
        println("Cash money ${cash2.currency.name}: ${cash2.amount}")

        account.deposit(cash2)
        println("Account ${account.money.currency.name}: ${account.money.amount}")
    } else {
        println("Cannot do cash2 checks")
    }

    println()
    val freeze = account.freeze("Test", bank)
    try {
        println(account.freeze("Test", bank2))
    } catch (e: IllegalArgumentException) {
        println("Could not freeze. ${e.message}")
    }
    println("Account frozen?: ${account.frozen}")
    account.unfreeze()
    println("Account frozen?: ${account.frozen}")

    val freeze2 = account.freeze("Test", nl)
    val freeze3 = account.freeze("Test", bank)
    try {
        println(account.freeze("Test", us))
    } catch (e: IllegalArgumentException) {
        println("Could not freeze. ${e.message}")
    }
    println("Account frozen?: ${account.frozen}")
    account.unfreezeById(freeze2.id)
    println("Account frozen?: ${account.frozen}")
    account.unfreeze()
    println("Account frozen?: ${account.frozen}")


}

class Account(val id: Long, val bank: Bank, var owner: String, val money: Digital) {

    val frozenHistory: MutableMap<UUID, Freeze> = mutableMapOf()
    val transactionHistory: MutableList<Transaction> = mutableListOf()
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

    fun transfer(account: Account, amount: Double, description: String = "Money transfer.", currency: Currency = money.currency): Boolean {
        val digital = Digital(amount, currency)
        println("Transfer has currency: ${currency.name}")
        val transaction = Transaction(this, account, digital, description)
        try {
            removeMoney(digital)
            transactionHistory += transaction
        } catch (e: FrozenException) {
            println("Cannot transfer money, executor account: ${e.message}")
            return false
        }

        try {
            account.addMoney(digital)
            account.transactionHistory += transaction
        } catch (e: FrozenException) {
            println("Cannot transfer money, target account: ${e.message}")
            addMoney(digital)
            transactionHistory.removeLast()
            return false
        }

        return true
    }

    private fun addMoney(money: Money) {
        frozenScan()

        val amount = exchangeCurrency(money.amount, money.currency, this.money.currency)

        if (amount <= 0) {
            throw InvalidBalanceException()
        }

        this.money.amount += amount
    }

    private fun removeMoney(money: Money) {
        frozenScan()

        val amount = exchangeCurrency(money.amount, money.currency, this.money.currency)

        if (amount <= 0) {
            throw InvalidBalanceException()
        }

        if (this.money.amount < amount) {
            throw NoBalanceException()
        }

        this.money.amount -= amount
    }

    private fun frozenScan() {
        if (bank.country.banned) {
            throw CountryFrozenException()
        }
        if (bank.banned) {
            throw BankFrozenException()
        }
        if (frozen) {
            throw AccountFrozenException()
        }
    }

    fun withdraw(amount: Double, country: Country = bank.country): Cash? {
        val newAmount = exchangeCurrency(amount, money.currency, country.currency)
        val cash = Cash(newAmount, country.currency, country)

        val transaction = Transaction(this, null, cash, "Withdrawal of cash.")
        try {
            removeMoney(cash)
        } catch (e: FrozenException) {
            println("Cannot withdraw money: ${e.message}")
            return null
        }
        transactionHistory += transaction

        return cash
    }

    fun deposit(cash: Cash): Boolean {
        val transaction = Transaction(null, this, cash, "Deposit of cash.")
        try {
            addMoney(cash)
        } catch (e: FrozenException) {
            println("Cannot withdraw money: ${e.message}")
            return false
        }

        transactionHistory += transaction
        return true
    }

    fun freeze(reason: String, executor: Holder, until: LocalDateTime? = null): Freeze {
        // TODO: Clean up code to be simplified.
        if (executor != bank) {
            if (executor is Country) {
                if (executor != bank.country) {
                    throw IllegalArgumentException("Can't freeze account if country is not the account's country.")
                }
            } else {
                throw IllegalArgumentException("Can't freeze account if bank is not the account's bank.")
            }
        }

        // TODO: Prevent freezing when until <= now.

        val freeze = Freeze(reason, executor, until)
        frozenHistory[freeze.id] = freeze

        return freeze
    }

    fun unfreeze() {
        if (!frozen) {
            return
        }

        frozenHistory.forEach {
            it.value.active = false
        }
    }

    fun unfreezeById(freezeId: UUID) {
        if (!frozen) {
            return
        }

        val freeze = frozenHistory[freezeId]
        if (freeze != null) {
            freeze.active = false
        } else {
            throw IllegalArgumentException("Could not find freeze by id.")
        }
    }

}
