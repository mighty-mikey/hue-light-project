package com.minds.great.hueLightProject.utils.dagger;

import com.minds.great.hueLightProject.core.domain.ConnectionDomain;
import com.minds.great.hueLightProject.core.domain.LightSystemDomain;
import com.minds.great.hueLightProject.core.domain.MainDomain;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.LightSystemInterface;
import com.minds.great.hueLightProject.core.presenters.LightListPresenter;
import com.minds.great.hueLightProject.core.presenters.MoodListPresenter;
import com.minds.great.hueLightProject.core.presenters.SingleLightPresenter;
import com.minds.great.hueLightProject.data.HueDomain;
import com.minds.great.hueLightProject.data.MoodDao;
import com.minds.great.hueLightProject.data.MoodRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    MainDomain providesMainController(HueDomain hueDomain, ConnectionDomain connectionDomain, LightSystemInterface lightSystemInterface){
        return new MainDomain(hueDomain, connectionDomain, lightSystemInterface);
    }

    @Singleton
    @Provides
    LightSystemDomain providesLightSystemDomain(ConnectionDomain connectionDomain){
        return new LightSystemDomain(connectionDomain);
    }

    @Singleton
    @Provides
    MoodListPresenter providesMoodListPresenter(LightSystemDomain domain){
        return new MoodListPresenter(domain);
    }
}
