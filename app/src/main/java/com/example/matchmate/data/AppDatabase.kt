package com.example.matchmate.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MatchProfileEntity::class, PendingActionEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun matchProfileDao(): MatchProfileDao
}