package nl.rickmartens.betterswiftbanking.models

class Country(name: String, code: String, var currency: Currency, override var banned: Boolean = false) : Holder {

    override var name: String = name
        set(value) = Unit

    override var code: String = code
        set(value) = Unit

}
