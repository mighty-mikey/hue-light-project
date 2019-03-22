package com.minds.great.hueLightProject.userInterface.fragments.Lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

public abstract class EvenOddAdapter extends BaseAdapter {
    private final int EVEN_ROW_CODE = 1;
    private final int ODD_ROW_CODE = -1;

    @Override
    public int getItemViewType(int position) {
        if (position %2 == 0) {
            return EVEN_ROW_CODE;
        } else {
            return ODD_ROW_CODE;
        }
    }

    protected View inflateView(int itemViewType, Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = null;
        if (inflater != null) {
            if (itemViewType == EVEN_ROW_CODE) {
                itemView = inflater.inflate(getEvenLayout(), null);
            } else {
                itemView = inflater.inflate(getOddLayout(), null);
            }
        }
        return itemView;
    }

    public abstract int getEvenLayout();

    public abstract int getOddLayout();
}
