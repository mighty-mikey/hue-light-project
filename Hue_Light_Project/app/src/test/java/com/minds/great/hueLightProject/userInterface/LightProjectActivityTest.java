package com.minds.great.hueLightProject.userInterface;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.minds.great.hueLightProject.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowIntent;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LightProjectActivityTest {

    private LightProjectActivity subject;

    @Before
    public void setUp() throws Exception {
        subject = Robolectric.buildActivity( LightProjectActivity.class )
                .create()
                .start()
                .resume()
                .get();
    }

    @Test
    public void mainActivity_shouldNotBeNull() throws Exception {
        assertNotNull(subject);
    }

    @Test
    public void navigateToLightActivity_checkIntentFired() throws Exception {
        FragmentManager supportFragmentManager = subject.getSupportFragmentManager();
        subject.navigateToLightListActivity();
        List<Fragment> fragments = supportFragmentManager.getFragments();

        assertThat(fragments).isNotEmpty();
        assertThat(fragments.get(0) instanceof LightsListFragment).isTrue();

    }

    @Test
    public void navigateToConnectionActivity_checkIntentFired() throws Exception {
        FragmentManager supportFragmentManager = subject.getSupportFragmentManager();
        subject.navigateToConnectionActivity();
        List<Fragment> fragments = supportFragmentManager.getFragments();

        assertThat(fragments).isNotEmpty();
        assertThat(fragments.get(0) instanceof ConnectionFragment).isTrue();
    }
}