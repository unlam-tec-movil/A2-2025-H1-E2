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
        override fun getAllFavUser(email: String): Flow<List<UserFavEntity>> =
            userFavDao.getAll(
                userOwnerEmail = email,
            )

        override suspend fun deleteUserFav(userFavEntity: UserFavEntity) {
            userFavDao.deleteUserFav(userFavEntity)
        }

        override suspend fun deleteAllUserFavByOwner(email: String) {
            userFavDao.deleteAllUserFavByOwner(email)
        }

        override suspend fun insertFavUser(
            userFavEntity: UserFavEntity,
            userOwnerEmail: String,
            idPost: Int,
        ) {
            val userExist = getUserFav(userFavEntity.author, userOwnerEmail)
            if (userExist != null) {
                userFavDao.deleteUserFav(userExist)
            } else {
                userFavDao.insertUserFav(userFavEntity)
            }
        }

        private suspend fun getUserFav(
            author: String,
            userOwnerEmail: String,
        ): UserFavEntity? = userFavDao.getUserFav(author, userOwnerEmail)
    }
