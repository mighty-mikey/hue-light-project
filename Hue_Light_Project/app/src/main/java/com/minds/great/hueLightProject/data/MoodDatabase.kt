package com.minds.great.hueLightProject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.minds.great.hueLightProject.core.models.Mood
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.minds.great.hueLightProject.data.MoodDao
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(entities = [Mood::class], version = 1, exportSchema = false)
abstract class MoodDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao
}