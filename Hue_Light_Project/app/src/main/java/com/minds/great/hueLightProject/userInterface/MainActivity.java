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
import com.minds.great.hueLightProject.core.presenters.ConnectionPresenter;
import com.minds.great.hueLightProject.core.presenters.ConnectionView;
import com.minds.great.hueLightProject.utils.dagger.DaggerInjector;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements ConnectionView {

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
    ConnectionPresenter connectionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerInjector.builder().build().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectionPresenter.viewLoaded(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connectionPresenter.viewUnloaded();
    }

    @Click(resName = "connectButton")
    public void searchForBridges() {
        checkInternetPermission();
        connectionPresenter.search();
    }

    @Override
    public void showProgressBar() {
        searchProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        searchProgressBar.setVisibility(GONE);
    }

    @Override
    public void showWaitingForConnection() {
        waitingForConnection.setVisibility(VISIBLE);
    }

    @Override
    public void hideConnectButton() {
        connectButton.setVisibility(GONE);
    }

    @Override
    public void showConnectButton() {
        connectButton.setVisibility(VISIBLE);
    }

    @Override
    public void navigateToLightActivity() {
        Intent intent = new Intent(this, LightsActivity.class);
        startActivity(intent);
    }

    @Override
    public void hideErrorMessage() {
        errorMessage.setVisibility(GONE);
        errorMessage.setText("");
    }

    @Override
    public void showErrorMessage(int code) {
        if (code == ConnectionError.NO_BRIDGE_FOUND_CODE) {
            errorMessage.setText(R.string.no_bridge_found);
            errorMessage.setVisibility(VISIBLE);
        }
    }


    private void checkInternetPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (permissionCheck != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET}, 1);
        }
    }
}