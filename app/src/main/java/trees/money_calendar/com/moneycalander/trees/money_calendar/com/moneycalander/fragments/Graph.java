package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import trees.money_calendar.com.moneycalander.R;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.adapter.GraphSlidingTabLayout;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.adapter.ViewPagerAdapterGraph;

/**
 * Created by trees on 6/5/15.
 */
public class Graph extends Fragment {


    /*
 *This Fragment has two basic components
 * 1)SlideTabLayout
 * This view is used to define the layout of SlideTabs and all the definition is wrritten in SlideTabLayoutClass
 *
 *
 * 2)ViewPager
 * This View is used to host three fragmnet on it under its frame
 * It specially hosts ADD, TRANSICTION AND GRPAH Fragmnets on it
 *
 */




    GraphSlidingTabLayout tabs;
    ViewPagerAdapterGraph graphPagerAdapter;
    ViewPager pager;
    public static final CharSequence titles[]={"IN","OUT","NET"};
    public static final int noOfTabs=3;
    FragmentManager fm;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.graphs,container,false);
        initialize(v);
        return v;
    }




    private void initialize(View v)
    {
       /*
         1)VIEW PAGER INITILIZATION
         *
         * first the ViewPagerAdapter Class is instantiated
         * then pager is linked with the ViewPager View
         * at this point both ViewPagerAdapter and ViewPager object is instantiated
          * and the ViewPager Object is set with the ViewPager instance

         */

        pager=(ViewPager)v.findViewById(R.id.graphPager);
        graphPagerAdapter=new ViewPagerAdapterGraph(getActivity().getSupportFragmentManager(), titles, noOfTabs);
        pager.setAdapter(graphPagerAdapter);


         /*
        *2)
        *
        *Here the SlidingTabsLayout refrence is initialized with corresponding xml view
        *then the tabs are set to distribute evenly
        * and tabs are integrated with custome color facility
        * and then tabs are integrated on ViewPager instance
        *
         */


        tabs=(GraphSlidingTabLayout)v.findViewById(R.id.graphSlidingTabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new GraphSlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        tabs.setSelectedIndicatorColors(getResources().getColor(R.color.primaryColor));
        tabs.setViewPager(pager);

    }
}
