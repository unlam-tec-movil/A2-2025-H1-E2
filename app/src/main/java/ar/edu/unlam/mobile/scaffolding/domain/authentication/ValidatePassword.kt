package ar.edu.unlam.mobile.scaffolding.domain.authentication

import javax.inject.Inject

class ValidatePassword
    @Inject
    constructor() {
        private val minLength = 4
        private val maxLength = 16

        fun validatePassword(password: String): ValidationResult {
            when {
                password.contains(" ") -> {
                    return ValidationResult(
                        successful = false,
                        errorMessage = "La contraseña no puede contener espacios en blanco",
                    )
                }

                password.isBlank() -> {
                    return ValidationResult(
                        successful = false,
                        errorMessage = "La contraseña no puede estar vacía.",
                    )
                }

                password.length < minLength -> {
                    return ValidationResult(
                        successful = false,
                        errorMessage = "La contraseña deberia contener mas de $minLength",
                    )
                }

                password.length > maxLength -> {
                    return ValidationResult(
                        successful = false,
                        errorMessage = "La contraseña no debe superar los $maxLength caracteres",
                    )
                }

                else -> {
                    return ValidationResult(
                        successful = true,
                    )
                }
            }
        }
    }
