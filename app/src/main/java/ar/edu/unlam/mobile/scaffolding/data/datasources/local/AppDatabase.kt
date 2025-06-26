package ar.edu.unlam.mobile.scaffolding.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.TuitDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.UserDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.FavPostEntity
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.TuitEntity
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserEntity

@Database(entities = [UserEntity::class, FavPostEntity::class, TuitEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao

    abstract fun getTuitDao(): TuitDao
}
