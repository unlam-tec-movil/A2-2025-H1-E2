package ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.TuitEntity

@Dao
interface TuitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTuit(tuit: TuitEntity)
}
