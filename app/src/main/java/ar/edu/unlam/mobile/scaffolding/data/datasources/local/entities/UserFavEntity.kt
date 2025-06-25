package ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "postFav_table")
data class FavPostEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "author") val name: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String,
)
