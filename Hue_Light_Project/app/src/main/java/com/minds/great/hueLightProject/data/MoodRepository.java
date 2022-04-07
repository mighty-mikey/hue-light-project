package com.minds.great.hueLightProject.data;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import dagger.hilt.android.qualifiers.ApplicationContext;

import android.os.AsyncTask;

import com.minds.great.hueLightProject.core.models.Mood;

import java.util.List;

import javax.inject.Inject;

public class MoodRepository {
    private final MoodDao moodDao;

    @Inject
    public MoodRepository(MoodDatabase moodDatabase) {
        moodDao = moodDatabase.moodDao();
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
