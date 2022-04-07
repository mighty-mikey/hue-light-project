package com.minds.great.hueLightProject.core.domain.domainInterfaces;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.models.ConnectionError;
import com.minds.great.hueLightProject.core.models.LightSystem;

import java.util.List;

import javax.inject.Inject;

public interface LightSystemInterface {
    void searchForLightSystems();

    PublishRelay<List<LightSystem>> getLightSystemListObservable();

    void connectToLightSystem(String lightSystemIpAddress);

    PublishRelay<ConnectionError> getErrorObservable();

    BehaviorRelay<LightSystem> getLightSystemObservable();

    PublishRelay<LightSystem> getLightsAndGroupsHeartbeatRelayObservable();
}
