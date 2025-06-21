package ar.edu.unlam.mobile.scaffolding.data.datasources.network.request

data class feedRequest(
    val userToken: String,
    val appToken: String,
    val page: Int,
    val onlyParents: Boolean = false
)
