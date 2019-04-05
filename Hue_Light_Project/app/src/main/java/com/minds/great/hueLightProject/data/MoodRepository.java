package com.minds.great.hueLightProject.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.minds.great.hueLightProject.core.models.Mood;

import java.util.List;

public class MoodRepository {
    private MoodDao moodDao;

    public MoodRepository(Application application) {
        MoodDatabase db = MoodDatabase.getDatabase(application);
        moodDao = db.moodDao();
    }

    public LiveData<List<Mood>> getAllMoods() {
        return moodDao.getAllMoods();
    }


    public void insert (Mood mood) {
        new InsertAsyncTask(moodDao).execute(mood);
    }

    public void deleteMood(Mood mood) { new DeleteAsyncTask(moodDao).execute(mood);
    }

    private static class InsertAsyncTask extends AsyncTask<Mood, Void, Void> {

        private MoodDao asyncTaskDao;

        InsertAsyncTask(MoodDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Mood... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Mood, Void, Void> {

        private MoodDao asyncTaskDao;

        DeleteAsyncTask(MoodDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Mood... params) {
            asyncTaskDao.deleteMood(params[0]);
            return null;
        }
    }
}
