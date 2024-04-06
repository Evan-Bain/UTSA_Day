package com.example.utsa_day.leaderboard.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leaderboard_table")
data class LeaderboardTable(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val score: Int
) {
    constructor(name: String, score: Int) : this(0, name, score)
}