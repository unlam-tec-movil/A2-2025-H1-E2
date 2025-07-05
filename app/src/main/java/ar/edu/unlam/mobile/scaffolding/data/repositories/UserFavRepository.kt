package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserFavEntity
import kotlinx.coroutines.flow.Flow

interface UserFavRepository {
    suspend fun insertFavUser(
        userFavEntity: UserFavEntity,
        emailLogged: String,
    )

    fun getAllNameUserFav(emailLogged: String): Flow<List<String>>

    fun getAllFavUser(emailLogged: String): Flow<List<UserFavEntity>>

    suspend fun deleteUserFav(userFavEntity: UserFavEntity)

    suspend fun deleteAllUserFavByOwner(emailLogged: String)
}
