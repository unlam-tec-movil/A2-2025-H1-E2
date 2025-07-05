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
    @Query("SELECT * FROM userFav_table WHERE email_logged = :emailLogged")
    fun getAll(emailLogged: String): Flow<List<UserFavEntity>>

    @Query("SELECT * FROM userFav_table WHERE author = :author AND email_logged = :emailLogged")
    suspend fun getUserFav(
        author: String,
        emailLogged: String,
    ): UserFavEntity?

    @Query("SELECT author FROM userFav_table WHERE email_Logged= :emailLogged")
    fun getNameUserFav(emailLogged: String): Flow<List<String>>

    @Query("SELECT EXISTS(SELECT 1 FROM userFav_table WHERE author = :author AND email_logged = :emailLogged)")
    suspend fun existsUserFav(
        author: String,
        emailLogged: String,
    ): Boolean

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertUserFav(userFavEntity: UserFavEntity)

    @Query("DELETE FROM userFav_table WHERE email_logged = :emailLogged")
    suspend fun deleteAllUserFavByOwner(emailLogged: String)

    @Delete
    suspend fun deleteUserFav(userFavEntity: UserFavEntity)
}
