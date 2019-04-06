package com.minds.great.hueLightProject.userInterfaceTests;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.minds.great.hueLightProject.BuildConfig;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;
import com.minds.great.hueLightProject.userInterface.fragments.ConnectionFragment;
import com.minds.great.hueLightProject.userInterface.fragments.tabFragment.TabFragment;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;


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
    public void navigateToLightFragment_checkFragmentManager() throws Exception {
        FragmentManager supportFragmentManager = subject.getSupportFragmentManager();
        subject.navigateToTabFragment();
        List<Fragment> fragments = supportFragmentManager.getFragments();

        assertThat(fragments).isNotEmpty();
        assertThat(fragments.get(0) instanceof TabFragment).isTrue();

    }

    @Test
    public void navigateToConnectionFragment_checkFragmentManager() throws Exception {
        FragmentManager supportFragmentManager = subject.getSupportFragmentManager();
        subject.navigateToConnectionFragment();
        List<Fragment> fragments = supportFragmentManager.getFragments();

        assertThat(fragments).isNotEmpty();
        assertThat(fragments.get(0) instanceof ConnectionFragment).isTrue();
    }
}