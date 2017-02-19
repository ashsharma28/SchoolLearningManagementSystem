package com.example.ashishsharma.temporayappname;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.viewpagerindicator.CirclePageIndicator;

public class OnboardingActivity extends FragmentActivity {
    private static final int NUM_PAGES = 4;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Button btnSkip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        btnSkip=(Button)findViewById(R.id.skip_login);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipToIndex(v);
            }
        });
        mPager = (ViewPager) findViewById(R.id.onboard_pager);
        mPagerAdapter = new OnboardingAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        CirclePageIndicator titleIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        titleIndicator.setViewPager(mPager);
    }
public void skipToIndex(View v){
    Intent intent=new Intent(this,MainActivity.class);
    startActivity(intent);
    finish();
}

    private class OnboardingAdapter extends FragmentStatePagerAdapter {
        public OnboardingAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return OnBoardFragment.newInstance(position);
        }
        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}