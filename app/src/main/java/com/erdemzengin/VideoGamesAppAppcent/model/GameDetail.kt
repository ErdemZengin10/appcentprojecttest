package com.erdemzengin.VideoGamesAppAppcent.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GameDetail(
    @PrimaryKey
    var id: String,
    val name: String,
    @SerializedName("background_image")
    val imageUrl: String,
    val description: String,
    val released: String,
    val metacritic: String
)