package com.sourabhverma.stocksimulator.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IndicesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIndices(indices: Indices?)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBhavCopy(bhavCopy: BhavCopy?)
    @Query("SELECT * FROM Indices")
    fun getAllIndices(): LiveData<List<Indices>>
    @Query("SELECT * FROM BhavCopy")
    fun getAllBhavCopy(): LiveData<BhavCopy>
}