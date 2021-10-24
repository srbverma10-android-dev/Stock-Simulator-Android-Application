package com.sourabhverma.stocksimulator.data

data class GeneralResponseModel(
    val code: Int,
    val data: Data,
    val hasNext: Boolean
)