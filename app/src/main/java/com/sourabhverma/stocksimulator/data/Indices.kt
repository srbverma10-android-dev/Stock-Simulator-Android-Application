package com.sourabhverma.stocksimulator.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Indices(
    @ColumnInfo(name = "change") val change: Double,
    @ColumnInfo(name = "uid") val uid: Int,
    @ColumnInfo(name = "code") val code: Int,
    @ColumnInfo(name = "current") val current: Double,
    @ColumnInfo(name = "graphData") val graphData: String,
    @ColumnInfo(name = "hasNext") val hasNext: Boolean,
    @ColumnInfo(name = "high") val high: Double,
    @ColumnInfo(name = "low") val low: Double,
    @ColumnInfo(name = "name") val name: String,
    @PrimaryKey val symbol: String
)