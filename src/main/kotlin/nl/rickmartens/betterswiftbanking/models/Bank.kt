package nl.rickmartens.betterswiftbanking.models

class Bank(override var name: String, override var code: String, var country: Country, override var banned: Boolean = false) : Holder {

}
