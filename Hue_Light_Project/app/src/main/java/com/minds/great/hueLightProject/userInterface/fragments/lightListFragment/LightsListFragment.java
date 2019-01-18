package com.minds.great.hueLightProject.userInterface.fragments.lightListFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.MainInterface;
import com.minds.great.hueLightProject.core.presenters.LightsListInterface;
import com.minds.great.hueLightProject.core.presenters.LightListPresenter;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.util.List;

import javax.inject.Inject;

public class LightsListFragment extends Fragment implements LightsListInterface {

    @Inject
    LightListPresenter lightListPresenter;
    private LightsListAdapter lightsListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lights_list, container, false);

        if(getActivity() instanceof LightProjectActivity) {
            ((LightProjectActivity) getActivity()).getInjector().inject(this);
        }

        lightsListAdapter = new LightsListAdapter(lightListPresenter);

        ListView lightsList = view.findViewById(R.id.lightsList);
        lightsList.setAdapter(lightsListAdapter);
        List<LightPoint> lightList = lightListPresenter.getLightList();
        lightsListAdapter.setLightsList(lightList, getContext());

        View saveButton = view.findViewById(R.id.save_mood);
        saveButton.setOnClickListener(view1 -> lightListPresenter.saveMood());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        lightListPresenter.viewLoaded(this);
    }

    @Override
    public void onPause() {
        lightListPresenter.viewUnloaded();
        super.onPause();
    }

    @Override
    public void updateLights(List<LightPoint> lightPoints) {
        getActivity().runOnUiThread(() -> lightsListAdapter.updateListData(lightPoints));
    }

    @Override
    public void navigateToSingleLightFragment() {
        ((MainInterface)getContext()).navigateToSingleLightFragment();
    }

    public static Fragment newInstance() {
        return new LightsListFragment();
    }
}
