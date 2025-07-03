package ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "userFav_table",
    indices = [Index(value = ["author", "owner_email"], unique = true)],
)
data class UserFavEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String,
    @ColumnInfo(name = "owner_email") val userOwnerEmail: String,
)
