package com.minds.great.hueLightProject.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

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
