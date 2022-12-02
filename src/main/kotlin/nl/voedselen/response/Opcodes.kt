package nl.voedselen.response

enum class Opcodes(val code: Int, val message: String) {

	GENERAL_ERROR(0, "General error (such as a malformed request body, amongst other things)"),
	
	TOKEN_INVALID_OR_EXPIRED(10001, "The token is not valid or has expired."),
	
	INVALID_EMAIL(20001, "The email is not a valid email."),
	INVALID_PASSWORD(
		20002,
		"The password does not meet the minimum criteria. (Min 8 char, 1 Upper, 1 Lower, 1 Numeric, 1 Special)"
	),
	NO_USER_OR_INCORRECT_PASSWORD(20003, "The user does not exist, or the password did not match."),
	EMAIL_TAKEN(20004, "Email has already been taken.");
	
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