package ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table WHERE id = 1")
    suspend fun getUser(): UserEntity?

    @Query("SELECT email FROM user_table WHERE id = 1")
    fun getEmail(): String

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT  COUNT(*) FROM user_table")
    suspend fun getUserCount(): Int

    @Query("SELECT EXISTS(SELECT 1 FROM user_table WHERE ID = 1)")
    suspend fun existUser(): Boolean

    @Query("DELETE FROM user_table WHERE id = 1")
    suspend fun logoutUser()

    @Query(
        "UPDATE user_table " +
            "SET name = :name, " +
            "avatarUrl = :avatar" +
            " WHERE id = 1",
    )
    suspend fun updateUser(
        name: String,
        avatar: String,
    )
}
