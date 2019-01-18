package com.minds.great.hueLightProject.core.domain;

import com.minds.great.hueLightProject.core.domain.domainInterfaces.LightSystemInterface;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.MainInterface;
import com.minds.great.hueLightProject.core.models.ConnectionError;
import com.minds.great.hueLightProject.data.HueDomain;

import javax.annotation.Nonnull;

import io.reactivex.disposables.Disposable;

public class MainDomain {

    private HueDomain hueDomain;
    private ConnectionDomain connectionDomain;
    private Disposable connectionSuccessDisposable;
    private Disposable connectionErrorDisposable;
    private LightSystemInterface lightSystemInterface;

    public MainDomain(HueDomain hueDomain, ConnectionDomain connectionDomain, LightSystemInterface lightSystemInterface) {
        this.hueDomain = hueDomain;
        this.connectionDomain = connectionDomain;
        this.lightSystemInterface = lightSystemInterface;
    }

    public void viewLoaded(@Nonnull MainInterface view) {
        String lightSystemIpAddress = hueDomain.getLastConnectedBridgeIpAddress();
        connectionSuccessDisposable = connectionDomain.getConnectionSuccessfulRelay()
                .subscribe(lightSystem -> view.navigateToTabFragment());
        connectionErrorDisposable = lightSystemInterface.getErrorObservable()
                .subscribe(connectionError -> {
                    if (connectionError.getCode() == ConnectionError.SAVED_BRIDGE_NOT_FOUND) {
                        view.navigateToConnectionFragment();
                    }
                });
        if (null != lightSystemIpAddress) {
            connectionDomain.connect(lightSystemIpAddress);
        } else {
            view.navigateToConnectionFragment();
        }
    }

    public void viewUnloaded() {
        if (connectionSuccessDisposable != null) {
            connectionSuccessDisposable.dispose();
            connectionSuccessDisposable = null;
        }
        if (connectionErrorDisposable != null) {
            connectionErrorDisposable.dispose();
            connectionErrorDisposable = null;
        }
    }
}
