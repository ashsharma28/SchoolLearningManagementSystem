package com.example.ashishsharma.temporayappname;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by kunal goyal on 23/10/2015.
 */
public class StudentIndexTabAdapter extends FragmentPagerAdapter{
   private Context context;
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Gallery","News","Events","Notices"};
      GalleryFragment galleryFragment;
      NewsFragment newsFragment;
      EventsFragment eventsFragment;
    notification notificationFragment;

    public StudentIndexTabAdapter(FragmentManager childFragmentManager, FragmentActivity activity) {
        super(childFragmentManager);
        this.context=activity;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                if(galleryFragment == null){
                    galleryFragment = GalleryFragment.newInstance();
                }
                return galleryFragment;
            case 1:
                if(newsFragment == null) {
                    newsFragment = NewsFragment.newInstance();
                }
                return newsFragment;
            case 2:
                if(eventsFragment == null){
                    eventsFragment = EventsFragment.newInstance();
                }
                return eventsFragment;
            case 3:
            if(notificationFragment == null){
                notificationFragment = notification.newInstance();
            }
            return notificationFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
