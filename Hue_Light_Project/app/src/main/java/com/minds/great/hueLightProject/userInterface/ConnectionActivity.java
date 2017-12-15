package com.minds.great.hueLightProject.userInterface;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.models.ConnectionError;
import com.minds.great.hueLightProject.core.controllers.ConnectionController;
import com.minds.great.hueLightProject.core.controllers.ConnectionView;
import com.minds.great.hueLightProject.utils.dagger.DaggerInjector;
import com.minds.great.hueLightProject.utils.dagger.HueModule;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

@EActivity(R.layout.activity_connection)
public class ConnectionActivity extends AppCompatActivity implements ConnectionView {

    @ViewById
    View searchProgressBar;
    @ViewById
    Button connectButton;
    @ViewById
    ConstraintLayout bridgeLayout;
    @ViewById
    TextView waitingForConnection;
    @ViewById
    TextView errorMessage;
    @Inject
    ConnectionController connectionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        DaggerInjector.builder().hueModule(new HueModule(this)).build().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectionController.viewLoaded(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connectionController.viewUnloaded();
    }

    @Click(resName = "connectButton")
    public void searchForBridges() {
        checkInternetPermission();
        connectionController.search();
    }

    @Override
    public void showProgressBar() {
        runOnUiThread(() -> searchProgressBar.setVisibility(VISIBLE));
    }

    @Override
    public void hideProgressBar() {
        runOnUiThread(() -> searchProgressBar.setVisibility(GONE));
    }

    @Override
    public void showWaitingForConnection() {runOnUiThread(() -> waitingForConnection.setVisibility(VISIBLE));}

    @Override
    public void showConnectButton() {
        runOnUiThread(() -> connectButton.setVisibility(VISIBLE));
    }

    @Override
    public void hideConnectButton() {
        runOnUiThread(() -> connectButton.setVisibility(GONE));
    }

    @Override
    public void showErrorMessage(int code) {
        runOnUiThread(() -> {
            if (ConnectionError.NO_BRIDGE_FOUND_CODE == code) {
                errorMessage.setText(R.string.no_bridge_found);
                errorMessage.setVisibility(VISIBLE);
            }
        });
    }

    @Override
    public void hideErrorMessage() {
        runOnUiThread(() -> {
            errorMessage.setVisibility(GONE);
            errorMessage.setText("");
        });
    }

    private void checkInternetPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (permissionCheck != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET}, 1);
        }
    }
}