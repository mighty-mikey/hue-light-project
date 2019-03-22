package com.minds.great.hueLightProject.userInterface.fragments.Lists.moodListFragment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.minds.great.hueLightProject.core.models.Mood;
import com.minds.great.hueLightProject.data.MoodRepository;

import java.util.List;

public class MoodListViewModel extends AndroidViewModel{

    private final MoodRepository moodRepository;

    public MoodListViewModel(@NonNull Application application) {
        super(application);
        moodRepository = new MoodRepository(application);
    }

    public LiveData<List<Mood>> getAllMoods(){
        return moodRepository.getAllMoods();
    }

    public void insert(Mood mood){
        moodRepository.insert(mood);
    }
}
