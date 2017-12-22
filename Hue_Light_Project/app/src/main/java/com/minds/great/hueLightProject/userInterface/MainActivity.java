package com.minds.great.hueLightProject.userInterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MainActivityInterface;
import com.minds.great.hueLightProject.core.controllers.MainController;
import com.minds.great.hueLightProject.utils.dagger.DaggerInjector;
import com.minds.great.hueLightProject.utils.dagger.HueModule;
import org.androidannotations.annotations.EActivity;
import javax.inject.Inject;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    @Inject
    MainController mainController;
    private final int CONNECTION_ACTIVITY_CODE = 0;
    private final int LIGHT_ACTIVITY_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        DaggerInjector.builder().hueModule(new HueModule(this)).build().inject(this);
        mainController.viewCreated(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        mainController.viewUnloaded();
        super.onDestroy();
    }

    @Override
    public void navigateToConnectionActivity() {
        Intent intent = new Intent(this, ConnectionActivity.class);
        startActivityForResult(intent, CONNECTION_ACTIVITY_CODE);
    }

    @Override
    public void finishConnectionActivity() {
        finishActivity(CONNECTION_ACTIVITY_CODE);
    }

    @Override
    public void navigateToLightListActivity() {
        Intent intent = new Intent(this, LightsListActivity.class);
        startActivityForResult(intent, LIGHT_ACTIVITY_CODE);
    }
}