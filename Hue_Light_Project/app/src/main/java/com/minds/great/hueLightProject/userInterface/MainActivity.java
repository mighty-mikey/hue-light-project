package com.minds.great.hueLightProject.userInterface;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.controllers.ConnectionController;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.minds.great.hueLightProject.core.presenters.ConnectionPresenter;
import com.minds.great.hueLightProject.core.presenters.ConnectionView;
import com.minds.great.hueLightProject.utils.HueViewError;
import com.minds.great.hueLightProject.utils.dagger.DaggerInjector;
import com.philips.lighting.hue.sdk.PHAccessPoint;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

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
    ConnectionController controller;
    @Inject
    ConnectionPresenter connectionPresenter;

    Disposable subscription;

    String userName;
    String ipAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerInjector.builder().build().inject(this);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        userName = prefs.getString("userName", null);
        ipAddress = prefs.getString("ipAddress", null);

        if (userName != null && ipAddress != null) {
            LightSystem controller = new LightSystem.Builder()
                    .userName(userName)
                    .ipAddress(ipAddress)
                    .build();
            this.controller.connectToController(controller);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectionPresenter.viewLoaded(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) {
            subscription.dispose();
            subscription = null;
        }
        connectionPresenter.viewUnloaded();
    }

    private void determineRelayObject(Object object) {
        if (object instanceof HueViewError) {
            showError((HueViewError) object);
        } else if (object instanceof List) {
            if (((List) object).size() > 0) {
                if (((List) object).get(0) instanceof PHAccessPoint) {
                    connectToFirstBridge((List) object);
                }
            }
        } else if (object instanceof LightSystem) {
            LightSystem lightSystem = (LightSystem) object;
            if (!lightSystem.getUserName().equals(userName)
                    || !lightSystem.getIpAddress().equals(ipAddress)) {
                SharedPreferences.Editor prefs = this.getPreferences(MODE_PRIVATE).edit();
                prefs.putString("userName", lightSystem.getUserName());
                prefs.putString("ipAddress", lightSystem.getIpAddress());
                prefs.commit();
            }
            Intent intent = new Intent(this, LightsActivity.class);
            startActivity(intent);
        }
    }

    private void showError(HueViewError error) {
        if (error.getCode() == HueViewError.NO_BRIDGE_FOUND_CODE) {
            this.runOnUiThread(() -> {
                errorMessage.setText(R.string.no_bridge_found);
                searchProgressBar.setVisibility(GONE);
                errorMessage.setVisibility(VISIBLE);
                connectButton.setVisibility(VISIBLE);
            });
        }
    }

    private void connectToFirstBridge(List<LightSystem> listOfFoundBridges) {
        this.runOnUiThread(() -> {
            controller.connectToController(listOfFoundBridges.get(0));
            bridgeLayout.setVisibility(GONE);
            waitingForConnection.setVisibility(VISIBLE);
            searchProgressBar.setVisibility(GONE);
            connectButton.setVisibility(GONE);
        });
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
    public void hideConnectButton() {
        connectButton.setVisibility(GONE);
    }

    @Override
    public void hideErrorMessage() {
        errorMessage.setVisibility(GONE);
    }

    private void checkInternetPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (permissionCheck != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET}, 1);
        }
    }
}