package com.minds.great.hueLightProject.userInterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.minds.great.hueLightProject.LightsListAdapter;
import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.controllers.MainController;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MainActivityView;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.minds.great.hueLightProject.utils.dagger.DaggerInjector;
import com.minds.great.hueLightProject.utils.dagger.HueModule;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements MainActivityView {

    @Inject
    MainController mainController;

    private LightSystem mainLightSystem;

    private final int CONNECTION_ACTIVITY_CODE = 0;

    private ListView lightsList;
    private ConstraintLayout lightsListView;
    private TextView logo;

    private void initViews() {
        lightsList = (ListView) findViewById(R.id.lightsList);
        lightsListView = (ConstraintLayout) findViewById(R.id.lightsListView);
        logo = (TextView) findViewById(R.id.logo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        DaggerInjector.builder().hueModule(new HueModule(this)).build().inject(this);
        mainController.viewCreated(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        initViews();
        super.onResume();
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
    public void switchToLightsList() {
        LightsListAdapter lightsListAdapter = new LightsListAdapter();
        runOnUiThread(() -> {
            lightsListAdapter.setLightsList(mainLightSystem.getPhBridge().getResourceCache().getAllLights(), this);
            lightsList.setAdapter(lightsListAdapter);
            logo.setVisibility(View.GONE);
            lightsListView.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void setMainLightSystem(LightSystem lightSystem) {
        mainLightSystem = lightSystem;
    }
}