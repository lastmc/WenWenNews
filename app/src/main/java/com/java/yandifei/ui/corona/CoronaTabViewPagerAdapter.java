package com.java.yandifei.ui.corona;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.java.yandifei.ui.knowledge.KnowledgeFragment;
import com.java.yandifei.ui.coronadata.CoronaDataListFragment;
import com.java.yandifei.ui.scholar.ScholarListFragment;

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
        switch (position){
            case 0:
                return new CoronaDataListFragment();
            case 1:
                return new KnowledgeFragment();
            case 3:
                return new ScholarListFragment();
        }
        return new Fragment();
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

}
