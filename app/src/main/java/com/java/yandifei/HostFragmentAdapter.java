package com.java.yandifei;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.java.yandifei.ui.corona.CoronaFragment;
import com.java.yandifei.ui.news.NewsFragment;

class HostFragmentAdapter extends FragmentStateAdapter {
    public HostFragmentAdapter(FragmentActivity activity){
        super(activity);
    }

    @Override
    public Fragment createFragment(int position){
        switch(position){
            case 0:
                return new NewsFragment();
            case 1:
                return new CoronaFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount(){
        return 2;
    }
}