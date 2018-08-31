package com.minds.great.hueLightProject.core.domain;

import com.minds.great.hueLightProject.core.domain.domainInterfaces.LightSystemInterface;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.MainInterface;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.MemoryInterface;
import com.minds.great.hueLightProject.core.models.ConnectionError;

import javax.annotation.Nonnull;

import io.reactivex.disposables.Disposable;

public class MainDomain {

    private MemoryInterface memory;
    private ConnectionDomain connectionDomain;
    private Disposable connectionSuccessDisposable;
    private Disposable connectionErrorDisposable;
    private LightSystemInterface lightSystemInterface;

    public MainDomain(MemoryInterface memory, ConnectionDomain connectionDomain, LightSystemInterface lightSystemInterface) {
        this.memory = memory;
        this.connectionDomain = connectionDomain;
        this.lightSystemInterface = lightSystemInterface;
    }

    public void viewLoaded(@Nonnull MainInterface view) {
        String lightSystemIpAddress = memory.getLightSystemIpAddress();
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
