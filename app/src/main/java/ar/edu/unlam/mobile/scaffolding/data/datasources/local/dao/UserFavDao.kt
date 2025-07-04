package ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserFavEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserFavDao {
    @Query("SELECT * FROM userFav_table WHERE user_owner_email= :userOwnerEmail")
    fun getAll(userOwnerEmail: String): Flow<List<UserFavEntity>>

    @Query("SELECT * FROM userFav_table WHERE author = :author AND user_owner_email = :userOwnerEmail")
    suspend fun getUserFav(
        author: String,
        userOwnerEmail: String,
    ): UserFavEntity?

    @Query("SELECT author FROM userFav_table WHERE user_owner_email= :userOwnerEmail")
    suspend fun getNameUserFav(userOwnerEmail: String): List<String>

    @Query("SELECT EXISTS(SELECT 1 FROM userFav_table WHERE author = :author AND user_owner_email = :userOwnerEmail)")
    suspend fun existsUserFav(
        author: String,
        userOwnerEmail: String,
    ): Boolean

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertUserFav(userFavEntity: UserFavEntity)

    @Query("DELETE FROM userFav_table WHERE user_owner_email = :userOwnerEmail")
    suspend fun deleteAllUserFavByOwner(userOwnerEmail: String)

    @Delete
    suspend fun deleteUserFav(userFavEntity: UserFavEntity)
}
