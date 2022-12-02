package nl.rickmartens.betterswiftbanking.models.account

import kotlinx.datetime.LocalDateTime
import nl.rickmartens.betterswiftbanking.models.holders.Bank
import nl.rickmartens.betterswiftbanking.models.holders.Holder
import nl.rickmartens.betterswiftbanking.models.money.Cash
import nl.rickmartens.betterswiftbanking.models.money.Money
import java.util.UUID

class Account(val id: Long, val bank: Bank, var owner: String, val money: Money) {

    val frozenHistory: Map<UUID, Freeze> = emptyMap()
    val transactionHistory: Array<Transaction> = arrayOf()
    val iban: () -> IBAN
        get() = {
            IBAN(this)
        }
    val frozen: () -> Boolean
        get() = {
            TODO("Check whether the account is frozen.")
        }

    fun transfer(target: IBAN, amount: Int) {
        TODO("Implement transfer code")
    }

    fun transfer(account: Account) {
        TODO("Implement transfer code")
    }

    fun withdraw(amount: Int) {
        TODO("Implement withdraw code")
    }

    fun deposit(cash: Cash) {
        TODO("Implement deposit code")
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
