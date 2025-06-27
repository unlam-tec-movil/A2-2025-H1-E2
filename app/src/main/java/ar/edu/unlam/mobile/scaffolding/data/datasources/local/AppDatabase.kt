package ar.edu.unlam.mobile.scaffolding.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.PostDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.UserDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.FavPostEntity
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.PostEntity
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserEntity

@Database(entities = [UserEntity::class, FavPostEntity::class, PostEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao

    abstract fun getPostDao(): PostDao
}
