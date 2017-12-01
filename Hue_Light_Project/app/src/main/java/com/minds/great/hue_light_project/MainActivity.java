package com.minds.great.hue_light_project;

import android.Manifest;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.minds.great.hue_light_project.Core.BridgeController;
import com.minds.great.hue_light_project.Core.BridgeListener;
import com.minds.great.hue_light_project.Utils.DaggerInjector;
import com.philips.lighting.hue.sdk.PHAccessPoint;


import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    View searchProgressBar;
    @ViewById
    Button searchButton;
    @ViewById
    ConstraintLayout bridgeLayout;
    @ViewById
    ListView bridgeList;
    @Inject
    BridgeController bridgeController;
    @Inject
    BridgeListener bridgeListener;
    @Inject
    BridgeListAdapter bridgeListAdapter;
    Disposable accessSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerInjector.builder().build().inject(this);
        accessSubscription = bridgeListener.getAccessPointRelay().subscribe(this::showBridgeList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(accessSubscription != null){
            accessSubscription.dispose();
            accessSubscription = null;
        }
    }

    private void showBridgeList(List<PHAccessPoint> listOfFoundBridges) {
        this.runOnUiThread(() -> {
            bridgeListAdapter.setBridgeList(listOfFoundBridges, this);
            bridgeList.setAdapter(bridgeListAdapter);

            searchProgressBar.setVisibility(View.GONE);
            searchButton.setVisibility(View.GONE);
            bridgeLayout.setVisibility(View.VISIBLE);
        });
    }

    @Click(resName = "searchButton")
    public void searchForBridges(){
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);

        if(permissionCheck != PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},1);
        }

        searchProgressBar.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.GONE);
        bridgeController.searchForBridges();
    }
}
