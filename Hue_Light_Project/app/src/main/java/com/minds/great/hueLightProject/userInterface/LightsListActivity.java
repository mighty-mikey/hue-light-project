package com.minds.great.hueLightProject.userInterface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.controllers.LightsListController;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.LightsListView;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.minds.great.hueLightProject.utils.dagger.DaggerInjector;
import com.minds.great.hueLightProject.utils.dagger.HueModule;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

@EActivity(R.layout.activity_light)
public class LightsListActivity extends AppCompatActivity implements LightsListView {

    @Inject
    LightsListController lightsListController;

    private ListView lightsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        DaggerInjector.builder().hueModule(new HueModule(this)).build().inject(this);
    }

    @Override
    public void populateLightsList(LightSystem lightSystem) {
        LightSystem lightSystem1 = lightSystem;
    }

    @Override
    protected void onResume() {
        initViews();
        lightsListController.viewLoaded(this);
        lightsListController.loadLightsList();
        super.onResume();
    }

    private void initViews() {
        lightsList = (ListView) findViewById(R.id.lightsList);
    }
}
