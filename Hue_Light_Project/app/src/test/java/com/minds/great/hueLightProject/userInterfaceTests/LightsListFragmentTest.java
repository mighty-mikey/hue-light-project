package com.minds.great.hueLightProject.userInterfaceTests;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.minds.great.hueLightProject.BuildConfig;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;
import com.minds.great.hueLightProject.userInterface.fragments.ConnectionFragment;
import com.minds.great.hueLightProject.userInterface.fragments.lightListFragment.LightsListFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LightsListFragmentTest {

    private LightsListFragment subject;
    private View view;

    @Before
    public void setUp() throws Exception {
        subject = new LightsListFragment();

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
    public void onCreateView_subscribesToLightsAndGroupsHeartbeatRelay() throws Exception {
        assertThat(Boolean.TRUE).isFalse();
    }
}
