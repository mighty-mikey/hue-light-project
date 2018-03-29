package com.minds.great.hueLightProject.core.domain;

import com.minds.great.hueLightProject.core.domain.domainInterfaces.MainInterface;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.MemoryInterface;

import javax.annotation.Nonnull;

import io.reactivex.disposables.Disposable;

public class MainDomain {

    private MemoryInterface memory;
    private ConnectionDomain connectionDomain;
    private Disposable connectionSuccessDisposable;

    public MainDomain(MemoryInterface memory, ConnectionDomain connectionDomain) {
        this.memory = memory;
        this.connectionDomain = connectionDomain;
    }

    public void viewLoaded(@Nonnull MainInterface view) {
        String lightSystemIpAddress = memory.getLightSystemIpAddress();
        connectionSuccessDisposable = connectionDomain.getConnectionSuccessfulRelay()
                .subscribe(lightSystem -> view.navigateToLightListFragment());
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
    }
}
