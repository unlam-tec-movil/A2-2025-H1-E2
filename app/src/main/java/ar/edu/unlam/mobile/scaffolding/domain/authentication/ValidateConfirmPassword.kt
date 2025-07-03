package ar.edu.unlam.mobile.scaffolding.domain.authentication

import javax.inject.Inject

class ValidateConfirmPassword
    @Inject
    constructor() {
        fun validateConfirmPassword(
            password: String,
            confirmPassword: String,
        ): ValidationResult =
            when {
                confirmPassword.isBlank() -> {
                    ValidationResult(
                        successful = false,
                        errorMessage = "ey no te olvides de este campo",
                    )
                }

                confirmPassword != password -> {
                    ValidationResult(
                        successful = false,
                        errorMessage = "Las contraseñas no coinciden",
                    )
                }

                else -> {
                    ValidationResult(
                        successful = true,
                    )
                }
            }
    }
