package ar.edu.unlam.mobile.scaffolding.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unlam.mobile.scaffolding.data.database.dao.UserDao
import ar.edu.unlam.mobile.scaffolding.data.database.entities.FavPostEntity
import ar.edu.unlam.mobile.scaffolding.data.database.entities.UserEntity

@Database(entities = [UserEntity::class, FavPostEntity::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}
