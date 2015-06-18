package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import trees.money_calendar.com.moneycalander.R;

/**
 * Created by trees on 6/5/15.
 */
public class NetResult extends Fragment

{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.net_result, container, false);
        return v;
    }
}
