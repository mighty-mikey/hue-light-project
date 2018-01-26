package com.minds.great.hueLightProject.userInterface;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.controllers.MainController;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.LightsListView;

import javax.inject.Inject;

public class LightsListFragment extends Fragment implements LightsListView{

    @Inject
    MainController mainController;

    private LightsListAdapter lightsListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(getActivity() instanceof LightProjectActivity) {
            ((LightProjectActivity) getActivity()).getInjector().inject(this);
        }
        lightsListAdapter = new LightsListAdapter();
        return inflater.inflate(R.layout.fragment_light, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ListView lightsList = (ListView) getActivity().findViewById(R.id.lightsList);
        lightsList.setAdapter(lightsListAdapter);
        lightsListAdapter.setLightsList(mainController.getLightList(), getContext());
        lightsListAdapter.notifyDataSetChanged();
    }
}
