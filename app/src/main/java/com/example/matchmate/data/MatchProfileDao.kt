package com.example.matchmate.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchProfileDao {
    @Query("SELECT * FROM match_profiles")
    fun getAllProfiles(): Flow<List<MatchProfileEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfiles(profiles: List<MatchProfileEntity>)

    @Update
    suspend fun updateProfile(profile: MatchProfileEntity)

    @Query("DELETE FROM match_profiles")
    suspend fun clearAll()

    @Query("SELECT * FROM match_profiles WHERE uuid = :id")
    suspend fun getProfileById(id: String): MatchProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPendingAction(action: PendingActionEntity)

    @Query("SELECT * FROM pending_actions")
    suspend fun getAllPendingActions(): List<PendingActionEntity>

    @Query("DELETE FROM pending_actions")
    suspend fun clearPendingActions()
}