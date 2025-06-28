package ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "avatarUrl") val avatarUrl: String?,
    @ColumnInfo(name = "token") val userToken: String,
)
