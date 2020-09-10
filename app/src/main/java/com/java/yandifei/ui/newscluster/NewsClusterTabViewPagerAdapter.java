package com.java.yandifei.ui.newscluster;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.java.yandifei.R;
import com.java.yandifei.ui.news.NewsListFragment;

import java.util.List;

public class NewsClusterTabViewPagerAdapter extends FragmentStateAdapter {
    public List<CharSequence> list;
    public NewsClusterTabViewPagerAdapter(List<CharSequence> list, FragmentActivity activity){
        super(activity);
        this.list = list;
        list.clear();
        list.add(activity.getString(R.string.theme0));
        list.add(activity.getString(R.string.theme1));
        list.add(activity.getString(R.string.theme2));
        list.add(activity.getString(R.string.theme3));
        list.add(activity.getString(R.string.theme4));
    }

    @Override
    public long getItemId(int position){
        return list.get(position).hashCode();
    }

    @Override
    public Fragment createFragment(int position){
        CharSequence tag = list.get(position);
        Bundle bundle = new Bundle();
        bundle.putCharSequence("tag", tag);
        bundle.putInt("theme", position);
        NewsClusterListFragment fragment = new NewsClusterListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

}
