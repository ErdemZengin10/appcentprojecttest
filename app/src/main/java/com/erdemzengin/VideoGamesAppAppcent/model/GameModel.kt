package com.erdemzengin.VideoGamesAppAppcent.model

import com.google.gson.annotations.SerializedName

data class GameModel(
    val id: String,
    val name: String,
    val released: String,
    @SerializedName("background_image")
    val imageUrl: String,
    val rating: Float
)