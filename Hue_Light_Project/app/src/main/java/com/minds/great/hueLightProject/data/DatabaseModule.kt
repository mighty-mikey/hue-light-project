package com.minds.great.hueLightProject.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun providesMoodDao(moodDatabase: MoodDatabase): MoodDao = moodDatabase.moodDao()

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): MoodDatabase {
        return synchronized(MoodDatabase::class.java) {
            Room.databaseBuilder(
                context.applicationContext,
                MoodDatabase::class.java, "mood_database"
            )
                .addCallback(sRoomDatabaseCallback)
                .build()
        }
    }


    private val sRoomDatabaseCallback: RoomDatabase.Callback = object : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
        }
    }
}

