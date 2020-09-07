package com.java.yandifei.ui.news;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.java.yandifei.ui.tab.TabSettingActivity;

import java.util.List;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;

public class NewsTabViewPagerAdapter extends FragmentStateAdapter {
    public List<CharSequence> list;
    public NewsTabViewPagerAdapter(List<CharSequence> list, FragmentActivity activity){
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
