package com.example.utsa_day.leaderboard.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.utsa_day.leaderboard.model.data.LeaderboardTable

@Dao
interface LeaderboardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(person: LeaderboardTable)

    @Query("SELECT * FROM leaderboard_table ORDER BY score DESC")
    suspend fun getLeaderboard(): List<LeaderboardTable>
}