package ar.edu.unlam.mobile.scaffolding.domain

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ar.edu.unlam.mobile.scaffolding.R

object FormValidator {
    private const val MIN_LENGTH = 4
    private const val MAX_LENGTH = 16

    private fun isBlank(text: String) = text.isBlank()

    private fun minLength(text: String) = text.length < MIN_LENGTH

    private fun maxLength(text: String) = text.length > MAX_LENGTH

    private fun hasInvalidCharacters(text: String) = !text.all { it.isLetterOrDigit() }

    @Composable
    fun errorText(
        text: String,
        minLength: Int = MIN_LENGTH,
        maxLength: Int = MAX_LENGTH,
    ): String? =
        when {
            isBlank(text) -> stringResource(R.string.empty_field)
            minLength(text) -> stringResource(R.string.text_tooShort, minLength)
            maxLength(text) -> stringResource(R.string.text_tooShort, maxLength)
            hasInvalidCharacters(text) -> stringResource(R.string.invalid_characters)
            else -> null
        }

    @Composable
    fun errorEmail(email: String): String? {
        val emailValid =
            android.util.Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()

        return if (emailValid) null else stringResource(R.string.invalid_email)
    }
}
