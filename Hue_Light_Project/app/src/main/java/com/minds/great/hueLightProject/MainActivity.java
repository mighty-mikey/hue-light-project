package com.minds.great.hueLightProject;

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
import android.widget.ListView;
import android.widget.TextView;

import com.minds.great.hueLightProject.core.LightSystemSharedPreferences;
import com.minds.great.hueLightProject.hueImpl.BridgeController;
import com.minds.great.hueLightProject.hueImpl.BridgeListener;
import com.minds.great.hueLightProject.utils.DaggerInjector;
import com.minds.great.hueLightProject.utils.HueViewError;
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

    Disposable subscription;

    String userName;
    String ipAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerInjector.builder().build().inject(this);
        subscription = bridgeListener.getPublishRelay().subscribe(this::determineRelayObject);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        userName = prefs.getString("userName", null);
        ipAddress = prefs.getString("ipAddress", null);

        if(userName != null && ipAddress != null) {
            PHAccessPoint phAccessPoint = new PHAccessPoint();
            phAccessPoint.setUsername(userName);
            phAccessPoint.setIpAddress(ipAddress);
            bridgeController.connectToBridge(phAccessPoint);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) {
            subscription.dispose();
            subscription = null;
        }
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
        } else if (object instanceof LightSystemSharedPreferences) {
            LightSystemSharedPreferences lightSystemSharedPreferences = (LightSystemSharedPreferences) object;
            if(!lightSystemSharedPreferences.getUserName().equals(userName)
                    || !lightSystemSharedPreferences.getIpAddress().equals(ipAddress)) {
                SharedPreferences.Editor prefs = this.getPreferences(MODE_PRIVATE).edit();
                prefs.putString("userName", lightSystemSharedPreferences.getUserName());
                prefs.putString("ipAddress", lightSystemSharedPreferences.getIpAddress());
                prefs.commit();
            }
            Intent intent = new Intent(this, LightsActivity_.class);
            startActivity(intent);
        }
    }

    private void showError(HueViewError error) {
        if (error.getCode() == HueViewError.NO_BRIDGE_FOUND_CODE) {
            this.runOnUiThread(() -> {
                errorMessage.setText(R.string.no_bridge_found);
                searchProgressBar.setVisibility(View.GONE);
                errorMessage.setVisibility(View.VISIBLE);
                connectButton.setVisibility(View.VISIBLE);
            });
        }
    }

    private void connectToFirstBridge(List<PHAccessPoint> listOfFoundBridges) {
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