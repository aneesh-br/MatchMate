package com.example.matchmate.data

import android.content.Context
import androidx.room.Room

object MatchDatabase {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "matchmate_database"
            )
                .fallbackToDestructiveMigration() // optionally handle migrations for now
                .build()
            INSTANCE = instance
            instance
        }
    }
}