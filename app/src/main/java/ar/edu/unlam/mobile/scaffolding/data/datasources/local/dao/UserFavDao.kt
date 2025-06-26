package ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserFavEntity

@Dao
interface UserFavDao {
    @Query("SELECT * FROM userFav_table")
    suspend fun getAll(): List<UserFavEntity>

//    @Query("SELECT author FROM userFav_table WHERE author = :author")
//    suspend fun getUserByName(author: String): String?

    @Query("SELECT EXISTS(SELECT 1 FROM userFav_table WHERE author = :author)")
    suspend fun existsByAuthor(author: String): Boolean

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertUserFav(userFavEntity: UserFavEntity)

    @Delete
    suspend fun deleteUserFav(userFavEntity: UserFavEntity)
}
