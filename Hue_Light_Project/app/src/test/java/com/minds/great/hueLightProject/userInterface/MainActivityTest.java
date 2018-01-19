package com.minds.great.hueLightProject.userInterface;

import android.content.Intent;
import android.view.View;

import com.minds.great.hueLightProject.BuildConfig;
import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.philips.lighting.hue.sdk.bridge.impl.PHBridgeImpl;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowIntent;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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

//    @Test
//    public void switchToLightsList_checkViewsVisibility() throws Exception {
//        View logo = subject.findViewById(R.id.logo);
//        View lightsListView = subject.findViewById(R.id.lightsListView);
//        Assertions.assertThat(logo.getVisibility() == View.VISIBLE).isTrue();
//        Assertions.assertThat(lightsListView.getVisibility() == View.GONE).isTrue();
//        subject.setMainLightSystem(new LightSystem.Builder()
//                .phBridge(new PHBridgeImpl()));
//        subject.switchToLightsList();
//        Assertions.assertThat(logo.getVisibility() == View.GONE).isTrue();
//        Assertions.assertThat(lightsListView.getVisibility() == View.VISIBLE).isTrue();
//    }

    @Test
    public void navigateToConnectionActivity_checkIntentFired() throws Exception {
        subject.navigateToConnectionActivity();
        Intent intent = shadowOf(subject).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(intent);
        assertEquals(ConnectionActivity.class, shadowIntent.getIntentClass());
    }
}