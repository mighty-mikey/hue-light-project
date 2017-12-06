package com.minds.great.hueLightProject.userInterfaceTests;

import com.minds.great.hueLightProject.BuildConfig;
import com.minds.great.hueLightProject.hueImpl.BridgeListener;
import com.minds.great.hueLightProject.MainActivity;
import com.minds.great.hueLightProject.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static junit.framework.Assert.assertNotNull;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private MainActivity subject;
    @Mock
    BridgeListener bridgeListenerMock;

    @Before
    public void setUp() throws Exception {
        subject = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void mainActivity_shouldNotBeNull() throws Exception {
        assertNotNull(subject);
    }

    @Test
    public void mainActivity_shouldHaveSearchButton() throws Exception {
        assertNotNull(subject.findViewById(R.id.connectButton));
    }
}
