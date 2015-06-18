package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import trees.money_calendar.com.moneycalander.R;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.adapter.TransListAdapter;

/**
 * Created by trees on 6/5/15.
 */
public class TransictionList extends Fragment {



    ListView list;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.transiction,container,false);
        list=(ListView)v.findViewById(R.id.transiction_list);
        context=(Context)getActivity();
        list.setAdapter(new TransListAdapter(context));
        return v;
    }


}



