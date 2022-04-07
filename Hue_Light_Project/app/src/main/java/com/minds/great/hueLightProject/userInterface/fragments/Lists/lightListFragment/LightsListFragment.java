package com.minds.great.hueLightProject.userInterface.fragments.Lists.lightListFragment;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import dagger.hilt.android.AndroidEntryPoint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.MainInterface;
import com.minds.great.hueLightProject.core.models.Mood;
import com.minds.great.hueLightProject.core.presenters.LightListPresenter;
import com.minds.great.hueLightProject.core.presenters.LightsListInterface;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.util.List;

import javax.inject.Inject;

@AndroidEntryPoint
public class LightsListFragment extends Fragment implements LightsListInterface {

    @Inject
    LightListPresenter lightListPresenter;
    private LightsListAdapter lightsListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lights_list, container, false);

        lightsListAdapter = new LightsListAdapter(lightListPresenter);

        ListView lightsList = view.findViewById(R.id.lightsList);
        lightsList.setAdapter(lightsListAdapter);
        List<LightPoint> lightList = lightListPresenter.getLightList();
        lightsListAdapter.setLightsList(lightList, getContext());

        View saveButton = view.findViewById(R.id.save_mood);


        saveButton.setOnClickListener(view1 -> {
            View dialogView = inflater.inflate(R.layout.save_mood_dialog, container, false);
            final EditText moodName = dialogView.findViewById(R.id.mood_name);
            final Button cancel = dialogView.findViewById(R.id.save_dialog_cancel);
            final Button save = dialogView.findViewById(R.id.save_dialog_save);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();

            cancel.setOnClickListener(view2 -> dismissDialog(cancel, save, alertDialog));

            save.setOnClickListener(view3 -> {
                saveLightListAsMood(moodName.getText().toString());
                dismissDialog(cancel, save, alertDialog);
            });
            alertDialog.show();
        });

        return view;
    }

    private void dismissDialog(Button cancel, Button save, AlertDialog alertDialog) {
        alertDialog.dismiss();
        cancel.setOnClickListener(null);
        save.setOnClickListener(null);
    }

    private void saveLightListAsMood(String name) {
        Mood mood = new Mood();
        mood.setName(name);
        mood.setListOfLights(lightListPresenter.getLightList());
        ((LightProjectActivity) getActivity()).getMoodListViewModel().insert(mood);
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
        ((MainInterface) getContext()).navigateToSingleLightFragment();
    }

    public static Fragment newInstance() {
        return new LightsListFragment();
    }
}
