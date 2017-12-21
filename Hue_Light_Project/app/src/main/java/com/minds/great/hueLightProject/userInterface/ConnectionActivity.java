package com.minds.great.hueLightProject.userInterface;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.models.ConnectionError;
import com.minds.great.hueLightProject.core.controllers.ConnectionController;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.ConnectionView;
import com.minds.great.hueLightProject.utils.dagger.DaggerInjector;
import com.minds.great.hueLightProject.utils.dagger.HueModule;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

@EActivity(R.layout.activity_connection)
public class ConnectionActivity extends AppCompatActivity implements ConnectionView {

    @Inject
    ConnectionController connectionController;

    private ProgressBar searchProgressBar;
    private Button connectButton;
    private TextView waitingForConnection;
    private TextView errorMessage;

    private final String EMPTY_STRING = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_connection);
        DaggerInjector.builder().hueModule(new HueModule(this)).build().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        initViews();
        connectButton.setOnClickListener(view -> {
            checkInternetPermission();
            connectionController.search();
        });
        connectionController.viewLoaded(this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        connectionController.viewUnloaded();
        super.onDestroy();
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
    public void showWaitingForConnection() {
        runOnUiThread(() -> waitingForConnection.setVisibility(VISIBLE));
    }

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
            errorMessage.setText(EMPTY_STRING);
        });
    }

    private void checkInternetPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (permissionCheck != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET}, 1);
        }
    }

    private void initViews() {
        searchProgressBar = (ProgressBar) findViewById(R.id.searchProgressBar);
        connectButton = (Button) findViewById(R.id.connectButton);
        waitingForConnection = (TextView) findViewById(R.id.waitingForConnection);
        errorMessage = (TextView) findViewById(R.id.errorMessage);
    }
}