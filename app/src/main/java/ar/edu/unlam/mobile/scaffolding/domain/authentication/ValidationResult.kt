package ar.edu.unlam.mobile.scaffolding.domain.authentication

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null,
)
