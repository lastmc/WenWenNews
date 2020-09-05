package com.java.yandifei.ui.news;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.java.yandifei.network.NewsEntry;

import java.util.List;

public class NewsTabViewPagerAdapter extends FragmentStateAdapter {
    List<? extends CharSequence> list;
    public NewsTabViewPagerAdapter(List<? extends CharSequence> list, FragmentActivity activity){
        super(activity);
        this.list = list;
    }

    @Override
    public long getItemId(int position){
        return list.get(position).hashCode();
    }

    @Override
    public Fragment createFragment(int position){
        return new NewsListFragment(list.get(position));
    }

    @Override
    public int getItemCount(){
        return list.size();
    }
}
