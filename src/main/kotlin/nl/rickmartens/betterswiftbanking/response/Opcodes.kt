package nl.rickmartens.betterswiftbanking.response

enum class Opcodes(val code: Int, val message: String) {

	GENERAL_ERROR(0, "General error (such as a malformed request body, amongst other things)");
	
	companion object {
		infix fun fromCode(code: Int): Opcodes {
			return try {
				Opcodes.values().first { it.code == code }
			} catch (ignored: NoSuchElementException) {
				GENERAL_ERROR
			}
		}
	}
	
}