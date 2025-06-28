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
    @Query("SELECT * FROM userFav_table")
    fun getAll(): Flow<List<UserFavEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM userFav_table WHERE author = :author)")
    suspend fun existsByAuthor(author: String): Boolean

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertUserFav(userFavEntity: UserFavEntity)

    @Query("DELETE FROM userFav_table")
    suspend fun deleteAllUserFav()

    @Delete
    suspend fun deleteUserFav(userFavEntity: UserFavEntity)
}
