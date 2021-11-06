package com.sourabhverma.stocksimulator.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IndicesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(indices: Indices?)
    @Query("SELECT * FROM Indices")
    fun getAll(): LiveData<List<Indices>>
}