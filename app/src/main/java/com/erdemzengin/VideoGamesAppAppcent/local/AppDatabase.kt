package com.erdemzengin.VideoGamesAppAppcent.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.erdemzengin.VideoGamesAppAppcent.model.GameDetail

@Database(entities = [GameDetail::class], version = 3)
abstract class AppDatabase: RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "game.db")
            .fallbackToDestructiveMigration()
            .build()
    }

}