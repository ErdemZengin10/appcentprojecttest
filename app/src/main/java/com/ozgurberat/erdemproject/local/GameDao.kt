package com.ozgurberat.erdemproject.local

import androidx.room.*
import com.ozgurberat.erdemproject.model.GameDetail

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: GameDetail)

    @Delete
    fun deleteGame(game: GameDetail)

    @Query("SELECT * FROM GameDetail")
    fun getAll(): List<GameDetail>


}