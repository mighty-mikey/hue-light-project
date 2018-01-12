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
public class HueMemoryTest {

    private HueMemory subject;

    @Before
    public void setUp() throws Exception {
        subject = new HueMemory(RuntimeEnvironment.application);
    }

    @Test
    public void saveLigthSystem_whenParameterNull_doesNotSave() throws Exception {
        subject.saveLightSystem(null);
        assertThat(subject.getLightSystemIpAddress()).isNull();
    }

    @Test
    public void saveLightSystem_whenUserNameIsNull_doesNotSave() throws Exception {
        String ipAddress = "1";
        LightSystem lightSystem = new LightSystem.Builder()
                .userName(null)
                .ipAddress(ipAddress)
                .build();

        subject.saveLightSystem(lightSystem);
        assertThat(subject.getLightSystemIpAddress()).isNull();
    }

    @Test
    public void saveLigthSystem_whenIpAddressIsNull_doesNotSave() throws Exception {
        String testUserName = "testUserName";
        LightSystem lightSystem = new LightSystem.Builder()
                .userName(testUserName)
                .ipAddress(null)
                .build();

        subject.saveLightSystem(lightSystem);
        assertThat(subject.getLightSystemIpAddress()).isNull();
    }

//    @Test
//    public void saveLightSystem_getLightSystem_returnsLightSystem() throws Exception {
//        LightSystem lightSystem = new LightSystem.Builder()
//                .userName(testUserName)
//                .ipAddress(ipAddress)
//                .build();
//
//        subject.saveLightSystem(lightSystem);
//        LightSystem returnedLightSystem = subject.getLightSystemIpAddress();
//
//        assertThat(returnedLightSystem.getUserName()).isNotNull();
//        assertThat(returnedLightSystem.getUserName().equals(testUserName)).isTrue();
//        assertThat(returnedLightSystem.getIpAddress()).isNotNull();
//        assertThat(returnedLightSystem.getIpAddress().equals(ipAddress)).isTrue();
//        assertThat(returnedLightSystem.getMacAddress()).isNull();
//        assertThat(returnedLightSystem.getBridgeId()).isNull();
//    }
}