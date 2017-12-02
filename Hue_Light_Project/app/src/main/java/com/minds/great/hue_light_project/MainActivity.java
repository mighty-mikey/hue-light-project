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
import android.widget.TextView;

import com.minds.great.hue_light_project.Core.BridgeController;
import com.minds.great.hue_light_project.Core.BridgeListener;
import com.minds.great.hue_light_project.Utils.DaggerInjector;
import com.minds.great.hue_light_project.Utils.HueViewError;
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
    Button connectButton;
    @ViewById
    ConstraintLayout bridgeLayout;
    @ViewById
    ListView bridgeList;
    @ViewById
    TextView waitingForConnection;
    @ViewById
    TextView errorMessage;
    @Inject
    BridgeController bridgeController;
    @Inject
    BridgeListener bridgeListener;
    @Inject
    BridgeListAdapter bridgeListAdapter;
    Disposable accessSubscription;
    Disposable errorSubscription;


    List<PHAccessPoint> listOfBridges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerInjector.builder().build().inject(this);
        accessSubscription = bridgeListener.getAccessPointRelay().subscribe(this::showBridgeList);
        errorSubscription = bridgeListener.getErrorRelay().subscribe(this::showError);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (accessSubscription != null) {
            accessSubscription.dispose();
            accessSubscription = null;
        }
        if (errorSubscription != null) {
            errorSubscription.dispose();
            errorSubscription = null;
        }
    }

    private void showError(HueViewError error) {
        if (error.getCode() == HueViewError.NO_BRIDGE_FOUND) {
            this.runOnUiThread(() -> {
                searchProgressBar.setVisibility(View.GONE);
                errorMessage.setText("No bridges found. Check network connection and try again.");
                errorMessage.setVisibility(View.VISIBLE);
                connectButton.setVisibility(View.VISIBLE);
            });
        }
    }

    private void showBridgeList(List<PHAccessPoint> listOfFoundBridges) {
        this.runOnUiThread(() -> {
            bridgeController.connectToBridge(listOfFoundBridges.get(0));
            bridgeLayout.setVisibility(View.GONE);
            waitingForConnection.setVisibility(View.VISIBLE);
            searchProgressBar.setVisibility(View.GONE);
            connectButton.setVisibility(View.GONE);

        });
    }

    @Click(resName = "connectButton")
    public void searchForBridges() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        if (permissionCheck != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET}, 1);
        }

        searchProgressBar.setVisibility(View.VISIBLE);
        connectButton.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
        bridgeController.searchForBridges();
    }
}