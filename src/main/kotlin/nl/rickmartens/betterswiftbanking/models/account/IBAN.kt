package nl.rickmartens.betterswiftbanking.models.account

class IBAN(iban: String) {

    var iban: String? = null
        private set

    init {
        this.iban = iban

        if (iban.length != 18) {
            throw IllegalArgumentException("IBAN is not valid.")
        }
    }

    constructor(account: Account) : this(account.bank.country.code + "00" + account.bank.code + account.id) {
        // TODO: Do magic to build the actually generate an IBAN.
        val accountId = account.id.toString()

        if (accountId.length != 10) {
            throw IllegalArgumentException("Account ID should be 10 characters long.")
        }
    }

}