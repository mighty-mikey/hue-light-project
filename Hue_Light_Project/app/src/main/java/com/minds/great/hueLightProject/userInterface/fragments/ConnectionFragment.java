package com.minds.great.hueLightProject.userInterface.fragments;

import android.Manifest;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import dagger.hilt.android.AndroidEntryPoint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.domain.ConnectionDomain;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.ConnectionInterface;
import com.minds.great.hueLightProject.core.models.ConnectionError;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;

import javax.inject.Inject;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

@AndroidEntryPoint
public class ConnectionFragment extends Fragment implements ConnectionInterface {

    @Inject
    ConnectionDomain connectionDomain;

    private ProgressBar searchProgressBar;
    private Button connectButton;
    private TextView waitingForConnection;
    private TextView errorMessage;
    private ImageView logo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connection, container, false);
    }

    @Override
    public void onResume() {
        initViews();
        connectButton.setOnClickListener(view -> {
            checkInternetPermission();
            connectionDomain.search();
        });
        connectionDomain.viewLoaded(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        connectionDomain.viewUnloaded();
        super.onPause();
    }

    @Override
    public void showProgressBar() {
        getActivity().runOnUiThread(() -> searchProgressBar.setVisibility(VISIBLE));
    }

    @Override
    public void hideProgressBar() {
        getActivity().runOnUiThread(() -> searchProgressBar.setVisibility(GONE));
    }

    @Override
    public void showWaitingForConnection() {
        getActivity().runOnUiThread(() -> waitingForConnection.setVisibility(VISIBLE));
    }

    @Override
    public void showConnectButton() {
        getActivity().runOnUiThread(() -> connectButton.setVisibility(VISIBLE));
    }

    @Override
    public void hideLogo() {
        getActivity().runOnUiThread(() -> logo.setVisibility(GONE));
    }

    @Override
    public void hideConnectButton() {
        getActivity().runOnUiThread(() -> connectButton.setVisibility(GONE));
    }

    @Override
    public void showErrorMessage(int code) {
        getActivity().runOnUiThread(() -> {
            if (ConnectionError.NO_BRIDGE_FOUND_CODE == code) {
                errorMessage.setText(R.string.no_bridge_found);
                errorMessage.setVisibility(VISIBLE);
            }
        });
    }

    @Override
    public void hideErrorMessage() {
        getActivity().runOnUiThread(() -> {
            String EMPTY_STRING = "";
            errorMessage.setVisibility(GONE);
            errorMessage.setText(EMPTY_STRING);
        });
    }

    private void checkInternetPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.INTERNET);
        if (permissionCheck != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.INTERNET}, 1);
        }
    }

    private void initViews() {
        View view = getView();
        if (null != view) {
            searchProgressBar = (ProgressBar) view.findViewById(R.id.searchProgressBar);
            connectButton = (Button) view.findViewById(R.id.connectButton);
            waitingForConnection = (TextView) view.findViewById(R.id.waitingForConnection);
            errorMessage = (TextView) view.findViewById(R.id.errorMessage);
            logo = (ImageView) view.findViewById(R.id.logo);
        }
    }
}