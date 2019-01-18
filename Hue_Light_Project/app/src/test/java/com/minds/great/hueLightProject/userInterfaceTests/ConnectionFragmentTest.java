package com.minds.great.hueLightProject.userInterfaceTests;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.minds.great.hueLightProject.BuildConfig;
import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.userInterface.fragments.ConnectionFragment;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.minds.great.hueLightProject.core.models.ConnectionError.NO_BRIDGE_FOUND_CODE;
import static junit.framework.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ConnectionFragmentTest {

    private ConnectionFragment subject;
    private View view;

    @Before
    public void setUp() throws Exception {
        subject = new ConnectionFragment();

        this.startFragment(subject);
        assertNotNull(subject);

        view = subject.getView();
        assertThat(view).isNotNull();
    }

    public void startFragment( Fragment fragment )
    {
        FragmentActivity activity = Robolectric.buildActivity( LightProjectActivity.class )
                .create()
                .start()
                .resume()
                .get();

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add( fragment, null );
        fragmentTransaction.commit();
    }


    @Test
    public void connectionActivity_shouldNotBeNull() throws Exception {
        assertNotNull(subject);
    }

    @Test
    public void connectionActivity_shouldHaveSearchButton() throws Exception {
        assertNotNull(view.findViewById(R.id.connectButton));
    }

    @Test
    public void connectionActivity_showProgressBar_hideProgressBar() throws Exception {
        View progressBar = view.findViewById(R.id.searchProgressBar);
        assertThat(progressBar.getVisibility() == View.GONE).isTrue();
        subject.showProgressBar();
        assertThat(progressBar.getVisibility() == View.VISIBLE).isTrue();
        subject.hideProgressBar();
        assertThat(progressBar.getVisibility() == View.GONE).isTrue();
    }

    @Test
    public void connectionActivity_showWaitingForConnection() throws Exception {
        View waitingForConnection = view.findViewById(R.id.waitingForConnection);
        assertThat(waitingForConnection.getVisibility() == View.GONE).isTrue();
        subject.showWaitingForConnection();
        assertThat(waitingForConnection.getVisibility() == View.VISIBLE).isTrue();
    }

    @Test
    public void connectionActivity_showConnectButton_hideConnectButton() throws Exception {
        View connectButton = view.findViewById(R.id.connectButton);
        assertThat(connectButton.getVisibility() == View.VISIBLE).isTrue();
        subject.hideConnectButton();
        assertThat(connectButton.getVisibility() == View.GONE).isTrue();
        subject.showConnectButton();
        assertThat(connectButton.getVisibility() == View.VISIBLE).isTrue();
    }

    @Test
    public void connectionActivity_showErrorMessage_hideErrorMessage() throws Exception {
        View errorMessage = view.findViewById(R.id.errorMessage);
        assertThat(errorMessage.getVisibility() == View.GONE).isTrue();
        subject.showErrorMessage(NO_BRIDGE_FOUND_CODE);
        assertThat(errorMessage.getVisibility() == View.VISIBLE).isTrue();
        subject.hideErrorMessage();
        assertThat(errorMessage.getVisibility() == View.GONE).isTrue();
    }
}
