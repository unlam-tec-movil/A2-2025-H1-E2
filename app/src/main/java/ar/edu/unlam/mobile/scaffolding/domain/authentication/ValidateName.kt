package ar.edu.unlam.mobile.scaffolding.domain.authentication

import javax.inject.Inject

class ValidateName
    @Inject
    constructor() {
        private val minLength = 4
        private val maxLength = 8

        fun validateName(name: String): ValidationResult {
            when {
                name.isBlank() -> {
                    return ValidationResult(
                        successful = false,
                        errorMessage = "El nombre no puede estar vacío",
                    )
                }

                name.length < minLength -> {
                    return ValidationResult(
                        successful = false,
                        errorMessage = "El nombre no puede ser menor a $minLength caracteres",
                    )
                }

                name.length > maxLength -> {
                    return ValidationResult(
                        successful = false,
                        errorMessage = "El nombre no debe superar los $maxLength caracteres",
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
