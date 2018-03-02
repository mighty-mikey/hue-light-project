package com.minds.great.hueLightProject.userInterface.fragments.lightListFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.controllers.LightSystemController;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.LightsListInterface;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.util.List;

import javax.inject.Inject;

public class LightsListFragment extends Fragment implements LightsListInterface {

    @Inject
    LightSystemController lightSystemController;

    private LightsListAdapter lightsListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(getActivity() instanceof LightProjectActivity) {
            ((LightProjectActivity) getActivity()).getInjector().inject(this);
        }
        lightsListAdapter = new LightsListAdapter(lightSystemController);
        return inflater.inflate(R.layout.fragment_lights_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ListView lightsList = (ListView) getActivity().findViewById(R.id.lightsList);
        lightsList.setAdapter(lightsListAdapter);
        List<LightPoint> lightList = lightSystemController.getLightList();
        lightsListAdapter.setLightsList(lightList, getContext());
        lightsListAdapter.notifyDataSetChanged();
        lightSystemController.viewLoaded(this);
    }

    @Override
    public void onDestroy() {
        lightSystemController.viewUnloaded();
        super.onDestroy();
    }

    @Override
    public void updateLights(LightSystem lightSystem) {
        getActivity().runOnUiThread(() -> lightsListAdapter.lightsAndGroupsHeartbeat(lightSystem.getBridge().getBridgeState().getLightPoints()));
    }
}
