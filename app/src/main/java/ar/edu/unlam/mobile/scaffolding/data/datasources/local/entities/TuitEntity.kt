package ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tuits")
data class TuitEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val content: String,
    val isDraft: Boolean = true
)
