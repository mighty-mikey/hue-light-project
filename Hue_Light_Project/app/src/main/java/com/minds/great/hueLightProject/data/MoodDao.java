package com.minds.great.hueLightProject.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.minds.great.hueLightProject.core.models.Mood;

import java.util.List;

@Dao
public interface MoodDao {

    @Insert
    void insert(Mood mood);

    @Delete
    void deleteMood(Mood mood);

    @Query("Select * from mood")
    LiveData<List<Mood>> getAllMoods();

}
