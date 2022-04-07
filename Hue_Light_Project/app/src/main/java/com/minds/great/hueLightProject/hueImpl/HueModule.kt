package com.minds.great.hueLightProject.hueImpl

import com.minds.great.hueLightProject.core.domain.domainInterfaces.LightSystemInterface
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class HueModule {
    @Binds
    abstract fun bindLightSystem(hueLightSystem: HueLightSystem): LightSystemInterface
}