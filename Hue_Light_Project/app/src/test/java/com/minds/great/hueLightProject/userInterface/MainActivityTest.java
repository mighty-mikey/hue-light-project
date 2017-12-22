package com.minds.great.hueLightProject.userInterface;

import android.content.Intent;

import com.minds.great.hueLightProject.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowIntent;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private MainActivity subject;

    @Before
    public void setUp() throws Exception {
        subject = Robolectric.buildActivity(MainActivity_.class)
                .get();
    }

    @Test
    public void mainActivity_shouldNotBeNull() throws Exception {
        assertNotNull(subject);
    }

    @Test
    public void navigateToLightActivity_checkIntentFired() throws Exception {
        subject.navigateToLightListActivity();
        Intent intent = shadowOf(subject).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(intent);
        assertEquals(LightsListActivity.class, shadowIntent.getIntentClass());
    }

    @Test
    public void navigateToConnectionActivity_checkIntentFired() throws Exception {
        subject.navigateToConnectionActivity();
        Intent intent = shadowOf(subject).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(intent);
        assertEquals(ConnectionActivity.class, shadowIntent.getIntentClass());
    }
}