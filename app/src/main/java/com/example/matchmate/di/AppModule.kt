package com.example.matchmate.di

import android.content.Context
import androidx.room.Room
import com.example.matchmate.data.AppDatabase
import com.example.matchmate.data.MatchProfileDao
import com.example.matchmate.network.MatchApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): MatchApiService =
        Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MatchApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "matchmate_database"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMatchProfileDao(db: AppDatabase): MatchProfileDao =
        db.matchProfileDao()
}