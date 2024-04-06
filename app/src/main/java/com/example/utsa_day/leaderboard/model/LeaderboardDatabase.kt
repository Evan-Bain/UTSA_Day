package com.example.utsa_day.leaderboard.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.utsa_day.leaderboard.model.data.LeaderboardTable

@Database(entities = [LeaderboardTable::class], version = 1, exportSchema = false)
abstract class LeaderboardDatabase : RoomDatabase() {

    abstract val profileImageDao: LeaderboardDao

    companion object {
        @Volatile
        private var INSTANCE: LeaderboardDatabase? = null

        fun getInstance(context: Context): LeaderboardDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LeaderboardDatabase::class.java,
                        "leaderboard_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }

}