package com.minds.great.hueLightProject.userInterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.controllers.MainActivityInterface;
import com.minds.great.hueLightProject.core.controllers.MainController;
import com.minds.great.hueLightProject.utils.dagger.DaggerInjector;
import com.minds.great.hueLightProject.utils.dagger.HueModule;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    @Inject
    MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerInjector.builder().hueModule(new HueModule(this)).build().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainController.viewLoaded(this);
    }

    @Override
    public void navigateToConnectionActivity() {
        Intent intent = new Intent(this, ConnectionActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void finishConnectionActivity() {
        finishActivity(0);
    }


}