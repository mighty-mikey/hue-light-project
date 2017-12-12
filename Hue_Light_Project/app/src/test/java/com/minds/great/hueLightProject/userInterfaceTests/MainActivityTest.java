package com.minds.great.hueLightProject.userInterfaceTests;

import android.content.Intent;
import android.view.View;

import com.minds.great.hueLightProject.BuildConfig;
import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.userInterface.LightsActivity;
import com.minds.great.hueLightProject.userInterface.MainActivity;
import com.minds.great.hueLightProject.userInterface.MainActivity_;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowIntent;

import static com.minds.great.hueLightProject.core.models.ConnectionError.NO_BRIDGE_FOUND_CODE;
import static junit.framework.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private MainActivity subject;

    @Before
    public void setUp() throws Exception {
        subject = Robolectric.buildActivity(MainActivity_.class)
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

    @Test
    public void mainActivity_showProgressBar_hideProgressBar() throws Exception {
        View progressBar = subject.findViewById(R.id.searchProgressBar);
        assertThat(progressBar.getVisibility() == View.GONE).isTrue();
        subject.showProgressBar();
        assertThat(progressBar.getVisibility() == View.VISIBLE).isTrue();
        subject.hideProgressBar();
        assertThat(progressBar.getVisibility() == View.GONE).isTrue();
    }

    @Test
    public void mainActivity_showWaitingForConnection() throws Exception {
        View waitingForConnection = subject.findViewById(R.id.waitingForConnection);
        assertThat(waitingForConnection.getVisibility() == View.GONE).isTrue();
        subject.showWaitingForConnection();
        assertThat(waitingForConnection.getVisibility() == View.VISIBLE).isTrue();
     }

    @Test
    public void mainActivity_showConnectButton_hideConnectButton() throws Exception {
        View connectButton = subject.findViewById(R.id.connectButton);
        assertThat(connectButton.getVisibility() == View.VISIBLE).isTrue();
        subject.hideConnectButton();
        assertThat(connectButton.getVisibility() == View.GONE).isTrue();
        subject.showConnectButton();
        assertThat(connectButton.getVisibility() == View.VISIBLE).isTrue();
    }

    @Test
    public void mainActivity_showErrorMessage_hideErrorMessage() throws Exception {
        View errorMessage = subject.findViewById(R.id.errorMessage);
        assertThat(errorMessage.getVisibility() == View.GONE).isTrue();
        subject.showErrorMessage(NO_BRIDGE_FOUND_CODE);
        assertThat(errorMessage.getVisibility() == View.VISIBLE).isTrue();
        subject.hideErrorMessage();
        assertThat(errorMessage.getVisibility() == View.GONE).isTrue();
    }

    @Test
    public void mainActivity_navigateToLightActivity() throws Exception {
        subject.navigateToLightActivity();
        Intent intent = shadowOf(subject).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(intent);
        assertEquals(LightsActivity.class, shadowIntent.getIntentClass());
    }
}
