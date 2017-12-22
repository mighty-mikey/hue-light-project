package com.minds.great.hueLightProject.userInterface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.minds.great.hueLightProject.R;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_light)
public class LightsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
    }
}
