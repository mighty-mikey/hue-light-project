package com.minds.great.hueLightProject.data;

import com.minds.great.hueLightProject.BuildConfig;
import com.minds.great.hueLightProject.core.models.LightSystem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MemoryTest {

    private Memory subject;
    private String testUserName = "testUserName";;
    private String ipAddress = "1";;

    @Before
    public void setUp() throws Exception {
        subject = new Memory(RuntimeEnvironment.application);
    }

    @Test
    public void saveLigthSystem_whenParameterNull_doesNotSave() throws Exception {
        subject.saveLightSystem(null);
        assertThat(subject.getLightSystem()).isNull();
    }

    @Test
    public void saveLigthSystem_whenUserNameIsNull_doesNotSave() throws Exception {
        LightSystem lightSystem = new LightSystem.Builder()
                .userName(null)
                .ipAddress(ipAddress)
                .build();

        subject.saveLightSystem(lightSystem);
        assertThat(subject.getLightSystem()).isNull();
    }

    @Test
    public void saveLigthSystem_whenIpAddressIsNull_doesNotSave() throws Exception {
        LightSystem lightSystem = new LightSystem.Builder()
                .userName(testUserName)
                .ipAddress(null)
                .build();

        subject.saveLightSystem(lightSystem);
        assertThat(subject.getLightSystem()).isNull();
    }

    @Test
    public void saveLightSystem_getLightSystem_returnsLightSystem() throws Exception {
        LightSystem lightSystem = new LightSystem.Builder()
                .userName(testUserName)
                .ipAddress(ipAddress)
                .build();

        subject.saveLightSystem(lightSystem);
        LightSystem returnedLightSystem = subject.getLightSystem();

        assertThat(returnedLightSystem.getUserName()).isNotNull();
        assertThat(returnedLightSystem.getUserName().equals(testUserName)).isTrue();
        assertThat(returnedLightSystem.getIpAddress()).isNotNull();
        assertThat(returnedLightSystem.getIpAddress().equals(ipAddress)).isTrue();
        assertThat(returnedLightSystem.getMacAddress()).isNull();
        assertThat(returnedLightSystem.getBridgeId()).isNull();
    }
}