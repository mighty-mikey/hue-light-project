package com.minds.great.hue_light_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.minds.great.hue_light_project.Core.BridgeController;
import com.minds.great.hue_light_project.Utils.DaggerInjector;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    View searchProgressBar;
    @ViewById
    View searchButton;
    @Inject
    BridgeController bridgeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerInjector.builder().build().inject(this);
    }

    @Click(resName = "searchButton")
    public void searchForBridges(){
        searchProgressBar.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.GONE);
        bridgeController.searchForBridges();
    }

}
