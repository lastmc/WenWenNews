package com.java.yandifei.ui.corona;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.java.yandifei.ui.news.NewsListFragment;

import java.util.List;

public class CoronaTabViewPagerAdapter extends FragmentStateAdapter {
    public List<CharSequence> list;
    public CoronaTabViewPagerAdapter(List<CharSequence> list, FragmentActivity activity){
        super(activity);
        this.list = list;
    }

    @Override
    public long getItemId(int position){
        return list.get(position).hashCode();
    }

    @Override
    public Fragment createFragment(int position){
        return new Fragment();
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

}
