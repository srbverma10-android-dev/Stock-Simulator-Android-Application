package com.sourabhverma.stocksimulator.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BhavCopy(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "code")  val code: Int,
    @ColumnInfo(name = "hasNext") val hasNext: Boolean,
    @ColumnInfo(name = "most_traded") val most_traded: String,
    @ColumnInfo(name = "top_gainer") val top_gainer: String,
    @ColumnInfo(name = "top_looser") val top_looser: String
)