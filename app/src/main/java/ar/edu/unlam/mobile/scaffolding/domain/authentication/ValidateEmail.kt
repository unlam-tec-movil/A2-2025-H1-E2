package ar.edu.unlam.mobile.scaffolding.domain.authentication

import android.util.Patterns
import javax.inject.Inject

class ValidateEmail
    @Inject
    constructor() {
        fun validateEmail(email: String): ValidationResult {
            when {
                email.isBlank() -> {
                    return ValidationResult(
                        successful = false,
                        errorMessage = "El email no puede estar vacío.",
                    )
                }

                !Patterns.EMAIL_ADDRESS
                    .matcher(email)
                    .matches() -> {
                    return ValidationResult(
                        successful = false,
                        errorMessage = "El email no es valido",
                    )
                } else -> {
                    return ValidationResult(
                        successful = true,
                    )
                }
            }
        }
    }
