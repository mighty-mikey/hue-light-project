package com.minds.great.hueLightProject.userInterface.fragments.Lists.moodListFragment;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

import com.minds.great.hueLightProject.core.models.Mood;
import com.minds.great.hueLightProject.data.DatabaseModule;
import com.minds.great.hueLightProject.data.MoodRepository;

import java.util.List;

import javax.inject.Inject;

@HiltViewModel
public class MoodListViewModel extends AndroidViewModel{

    private final MoodRepository moodRepository;

    @Inject
    public MoodListViewModel(@ApplicationContext @NonNull Context context,
                             MoodRepository moodRepository) {
        super((Application) context);
        this.moodRepository = moodRepository;
    }

    public LiveData<List<Mood>> getAllMoods(){
        return moodRepository.getAllMoods();
    }

    public void insert(Mood mood){
        moodRepository.insert(mood);
    }

    public void deleteMood(Mood mood) { moodRepository.deleteMood(mood); }
}
