package com.example.matchmate.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.matchmate.model.MatchStatus

@Entity(tableName = "pending_actions")
data class PendingActionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val status: MatchStatus
)