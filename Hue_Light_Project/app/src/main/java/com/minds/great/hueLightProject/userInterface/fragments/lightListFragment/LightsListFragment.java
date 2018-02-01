package com.minds.great.hueLightProject.userInterface.fragments.lightListFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.controllers.LightSystemController;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.LightsListView;
import com.minds.great.hueLightProject.core.models.ConnectionError;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

import static android.view.View.VISIBLE;

public class LightsListFragment extends Fragment implements LightsListView{

    @Inject
    LightSystemController lightSystemController;

    private LightsListAdapter lightsListAdapter;
    private Disposable lightAndGroupsHeartbeatDisposable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(getActivity() instanceof LightProjectActivity) {
            ((LightProjectActivity) getActivity()).getInjector().inject(this);
        }
        lightsListAdapter = new LightsListAdapter();
        lightAndGroupsHeartbeatDisposable = lightSystemController.getLightsAndGroupsHeartbeatRelay()
                .subscribe(lightSystem -> getActivity().runOnUiThread(() -> lightsListAdapter.lightsAndGroupsHeartbeat(lightSystem.getBridge().getBridgeState().getLightPoints())));
        return inflater.inflate(R.layout.fragment_light, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ListView lightsList = (ListView) getActivity().findViewById(R.id.lightsList);
        lightsList.setAdapter(lightsListAdapter);
        List<LightPoint> lightList = lightSystemController.getLightList();
        lightsListAdapter.setLightsList(lightList, getContext());
        lightsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        if (lightAndGroupsHeartbeatDisposable != null) {
            lightAndGroupsHeartbeatDisposable.dispose();
            lightAndGroupsHeartbeatDisposable = null;
        }
        super.onDestroy();
    }
}
