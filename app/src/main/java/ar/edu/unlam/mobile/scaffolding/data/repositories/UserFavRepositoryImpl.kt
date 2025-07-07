package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.UserFavDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserFavEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserFavRepositoryImpl
    @Inject
    constructor(
        private val userFavDao: UserFavDao,
    ) : UserFavRepository {
        override fun getAllFavUser(emailLogged: String): Flow<List<UserFavEntity>> =
            userFavDao.getAll(
                emailLogged = emailLogged,
            )

        override suspend fun deleteUserFav(userFavEntity: UserFavEntity) {
            userFavDao.deleteUserFav(userFavEntity)
        }

        override suspend fun deleteAllUserFavByOwner(emailLogged: String) {
            userFavDao.deleteAllUserFavByOwner(emailLogged)
        }

        override suspend fun followUser(
            userFavEntity: UserFavEntity,
            emailLogged: String,
        ) {
            val userExist = getUserFav(userFavEntity.author, emailLogged)
            if (userExist != null) {
                deleteUserFav(userExist)
            } else {
                insertUserFav(userFavEntity)
            }
        }

        private suspend fun insertUserFav(userFavEntity: UserFavEntity) {
            userFavDao.insertUserFav(userFavEntity)
        }

        override fun getAllNameUserFav(emailLogged: String): Flow<List<String>> = userFavDao.getNameUserFav(emailLogged)

        private suspend fun getUserFav(
            author: String,
            emailLogged: String,
        ): UserFavEntity? = userFavDao.getUserFav(author, emailLogged)
    }
