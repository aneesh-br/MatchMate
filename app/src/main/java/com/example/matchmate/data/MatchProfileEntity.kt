package com.example.matchmate.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.matchmate.model.MatchStatus

@Entity(tableName = "match_profiles")
data class MatchProfileEntity(
    @PrimaryKey val uuid: String,
    val name: String,
    val age: Int,
    val city: String,
    val state: String,
    val country: String,
    val imageUrl: String,
    val status: MatchStatus
)