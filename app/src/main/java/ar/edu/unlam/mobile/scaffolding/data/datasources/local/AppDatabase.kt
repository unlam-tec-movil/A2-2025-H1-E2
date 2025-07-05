package ar.edu.unlam.mobile.scaffolding.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.PostDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.UserDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.UserFavDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.PostEntity
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserEntity
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserFavEntity

@Database(entities = [UserEntity::class, UserFavEntity::class, PostEntity::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao

    abstract fun getUserFavDao(): UserFavDao

    abstract fun getPostDao(): PostDao
}
