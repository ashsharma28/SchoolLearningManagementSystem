package com.example.ashishsharma.temporayappname;

/**
 * Created by kunal goyal on 23/10/2015.
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentIndex extends Fragment{

    private StudentIndexTabAdapter indexTabAdapter;
    private View rootView;

    public StudentIndex() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.student_index_fragment, container, false);
        initTabBarLayout(rootView);
        return rootView;

    }


    private void initTabBarLayout(View rootView){

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        indexTabAdapter = new StudentIndexTabAdapter(this.getChildFragmentManager(),
                this.getActivity());
        viewPager.setAdapter(indexTabAdapter);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Gallery "));
        tabLayout.addTab(tabLayout.newTab().setText("News"));
        tabLayout.addTab(tabLayout.newTab().setText("Events "));
        //tabLayout.addTab(tabLayout.newTab().setText("Notices"));
        tabLayout.setupWithViewPager(viewPager);

       /* slidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.sliding_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.app_tab);
            }
        });
*/
    }

}



