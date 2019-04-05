package com.minds.great.hueLightProject.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.minds.great.hueLightProject.core.models.Mood;

@Database(entities = {Mood.class}, version = 1, exportSchema = false)
public abstract class MoodDatabase extends RoomDatabase {

    public abstract MoodDao moodDao();

    private static volatile MoodDatabase INSTANCE;

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                }
            };


    static MoodDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MoodDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MoodDatabase.class, "mood_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}



