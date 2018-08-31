package com.minds.great.hueLightProject.userInterface.fragments.tabFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;

public class TabFragment extends Fragment {

    private final int FIRST_PAGE = 0;
    private final int LAST_PAGE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);

        if (getActivity() instanceof LightProjectActivity) {
            ((LightProjectActivity) getActivity()).getInjector().inject(this);
        }

        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager());

        ViewPager tabs = (ViewPager) view.findViewById(R.id.pager);
        tabs.setAdapter(tabAdapter);
        Button button = (Button) view.findViewById(R.id.goto_first);
        button.setOnClickListener(v -> tabs.setCurrentItem(FIRST_PAGE));
        button = (Button) view.findViewById(R.id.goto_last);
        button.setOnClickListener(v -> tabs.setCurrentItem(LAST_PAGE));

        return view;
    }
}
