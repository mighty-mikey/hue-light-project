package com.minds.great.hueLightProject.core.presenters;

import com.minds.great.hueLightProject.core.domain.LightSystemDomain;
import com.minds.great.hueLightProject.core.models.Mood;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MoodListPresenterTest {

    @Mock
    LightSystemDomain lightSystemDomain;

    @Mock
    LightPoint bridgeLight1;
    @Mock
    LightPoint bridgeLight2;
    private MoodListPresenter subject;
    private Mood mood;
    private ArrayList<LightPoint> lightPoints;
    private LightState moodLightState1;
    private LightState moodLightState2;
    private ArrayList<LightPoint> bridgeLights;

    @Before
    public void setUp() {
        subject = new MoodListPresenter(lightSystemDomain);
        mood = new Mood();
        bridgeLights = new ArrayList<>();
        bridgeLights.add(bridgeLight1);
        lightPoints = new ArrayList<>();
        moodLightState1 = new LightState();
        moodLightState1.setOn(true);
        moodLightState1.setBrightness(4);
        lightPoints.add(new LightPoint(1, "Test1", moodLightState1));
        lightPoints.add(new LightPoint(2, "Test2", moodLightState2));
        mood.setListOfLights(lightPoints);
    }

    @Test
    public void selectSavedMood_callsLightSystemDomain_getsLightList() {
        subject.selectSavedMood(mood);
        verify(lightSystemDomain).getLightList();
    }

    @Test
    public void selectSavedMood_updatesBridgeLightWithAllMoodLightState() {
        when(bridgeLight1.getIdentifier()).thenReturn("I'm not a real ID... fool.");
        when(bridgeLight2.getIdentifier()).thenReturn("Test2");
        bridgeLights.add(bridgeLight2);
        when(lightSystemDomain.getLightList()).thenReturn(bridgeLights);
        subject.selectSavedMood(mood);
        verify(bridgeLight1, never()).updateState(moodLightState1);
        verify(bridgeLight2).updateState(moodLightState2);
    }

}