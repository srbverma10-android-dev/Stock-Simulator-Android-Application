package com.sourabhverma.stocksimulator.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Indices::class, BhavCopy::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun indicesDao(): IndicesDAO
}