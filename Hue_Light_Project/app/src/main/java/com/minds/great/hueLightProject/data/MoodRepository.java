package com.minds.great.hueLightProject.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.minds.great.hueLightProject.core.models.Mood;

import java.util.List;

public class MoodRepository {
    private MoodDao moodDao;
    private LiveData<List<Mood>> allMoods;

    public MoodRepository(Application application) {
        MoodDatabase db = MoodDatabase.getDatabase(application);
        moodDao = db.moodDao();
        allMoods = moodDao.getAllMoods();
    }

    public LiveData<List<Mood>> getAllMoods() {
        return allMoods;
    }


    public void insert (Mood word) {
        new insertAsyncTask(moodDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Mood, Void, Void> {

        private MoodDao asyncTaskDao;

        insertAsyncTask(MoodDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Mood... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
