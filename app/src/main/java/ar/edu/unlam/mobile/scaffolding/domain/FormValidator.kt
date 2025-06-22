package ar.edu.unlam.mobile.scaffolding.domain

object FormValidator {
    private const val MIN_LENGTH = 4
    private const val MAX_LENGTH = 16
    private const val EMPTY_FIELD = "El campo no puede estar vacío"
    private const val INVALID_CHARACTERS = "El campo no puede contener caracteres especiales"
    private const val INVALID_EMAIL = "El email no es valido"

    private fun isBlank(text: String) = text.isBlank()

    private fun minLength(text: String) = text.length < MIN_LENGTH

    private fun maxLength(text: String) = text.length > MAX_LENGTH

    private fun hasInvalidCharacters(text: String) = !text.all { it.isLetterOrDigit() }

    fun isValidText(
        text: String,
        minLength: Int = MIN_LENGTH,
        maxLength: Int = MAX_LENGTH,
        specialCharacters: Boolean = false,
    ): String? =
        when {
            isBlank(text) -> EMPTY_FIELD
            minLength(text) -> "El campo debe tener al menos $minLength caracteres"
            maxLength(text) -> "El campo no puede superar los $maxLength caracteres"
            !specialCharacters && hasInvalidCharacters(text) -> INVALID_CHARACTERS
            else -> null
        }

    fun isValidPassword(
        password: String,
        confirmPassword: String,
    ): String? {
        if (confirmPassword.isBlank()) {
            return EMPTY_FIELD
        } else if (password == confirmPassword) {
            return null
        } else {
            return "Las contraseñas no coinciden"
        }
    }

    fun isValidEmail(email: String): String? {
        val emailValid =
            android.util.Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()

        if (email.isBlank()) return EMPTY_FIELD
        return if (emailValid) null else INVALID_EMAIL
    }
}
