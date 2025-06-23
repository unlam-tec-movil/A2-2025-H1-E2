package ar.edu.unlam.mobile.scaffolding.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ar.edu.unlam.mobile.scaffolding.data.database.entities.UserEntity

@Dao
interface UserDao {
    private companion object {
        const val ID = 1
    }

    @Query("SELECT * FROM user_table")
    suspend fun getUser(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

//    @Delete
//    suspend fun deleteUser(userEntity: UserEntity)

    @Query("UPDATE user_table SET avatarUrl = :avatar WHERE id = ID")
    suspend fun updateAvatarUser(avatar: String)
}
