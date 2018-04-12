package com.minds.great.hueLightProject.utils.dagger;

import com.minds.great.hueLightProject.core.domain.ConnectionDomain;
import com.minds.great.hueLightProject.core.domain.LightSystemDomain;
import com.minds.great.hueLightProject.core.domain.MainDomain;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.LightSystemInterface;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.MemoryInterface;
import com.minds.great.hueLightProject.core.presenters.LightListPresenter;
import com.minds.great.hueLightProject.core.presenters.SingleLightPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
class LightProjectModule {

    @Singleton
    @Provides
    SingleLightPresenter providesSingleLightPresenter(ConnectionDomain connectionDomain, LightSystemDomain lightSystemDomain){
        return new SingleLightPresenter(connectionDomain, lightSystemDomain);
    }

    @Singleton
    @Provides
    LightListPresenter providesLightListPresenter(ConnectionDomain connectionDomain, LightSystemDomain lightSystemDomain){
        return new LightListPresenter(connectionDomain, lightSystemDomain);
    }

    @Singleton
    @Provides
    ConnectionDomain providesConnectionController(LightSystemInterface lightSystemInterface){
        return new ConnectionDomain(lightSystemInterface);
    }

    @Singleton
    @Provides
    MainDomain providesMainController(MemoryInterface memory, ConnectionDomain connectionDomain, LightSystemInterface lightSystemInterface){
        return new MainDomain(memory, connectionDomain, lightSystemInterface);
    }

    @Singleton
    @Provides
    LightSystemDomain providesLightSystemController(ConnectionDomain connectionDomain){
        return new LightSystemDomain(connectionDomain);
    }
}
