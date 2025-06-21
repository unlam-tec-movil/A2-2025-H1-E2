package ar.edu.unlam.mobile.scaffolding.data.datasources.network.request

data class EditUserRequest(
    val name: String,
    val avatar_url: String,
    val password: String,
)
