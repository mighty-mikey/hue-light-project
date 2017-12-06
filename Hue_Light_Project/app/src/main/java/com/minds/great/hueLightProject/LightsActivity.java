package com.minds.great.hueLightProject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_lights)
public class LightsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights);
    }
}