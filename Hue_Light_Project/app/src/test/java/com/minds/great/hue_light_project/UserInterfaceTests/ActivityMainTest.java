package com.minds.great.hue_light_project.UserInterfaceTests;

import com.minds.great.hue_light_project.BuildConfig;
import com.minds.great.hue_light_project.MainActivity;
import com.minds.great.hue_light_project.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static junit.framework.Assert.assertNotNull;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ActivityMainTest {


    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
    }


    @Test
    public void mainActivity_shouldNotBeNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void mainActivity_shouldHaveSearchButton() throws Exception {
        assertNotNull(activity.findViewById(R.id.searchButton));
    }
}
