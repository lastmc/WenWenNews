package com.java.yandifei.ui.corona;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.java.yandifei.R;

import java.util.ArrayList;
import java.util.List;

public class CoronaFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_corona, container, false);
        List<CharSequence> list = new ArrayList<>();
        list.add(getString(R.string.tab_corona_data));
        list.add(getString(R.string.tab_corona_knowledge));
        list.add(getString(R.string.tab_corona_news));
        list.add(getString(R.string.tab_corona_scholar));

        bindTabWithViewPager(root,list);
        setHasOptionsMenu(true);
        return root;
    }

    private void bindTabWithViewPager(View view,final List<CharSequence> list){
        final TabLayout tabLayout = view.findViewById(R.id.corona_tabs);
        final ViewPager2 viewPager2 = view.findViewById(R.id.corona_container);
        final CoronaTabViewPagerAdapter adapter = new CoronaTabViewPagerAdapter(list,getActivity());
        for(CharSequence c:list)
            tabLayout.addTab(tabLayout.newTab().setText(c));

        viewPager2.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = list.indexOf(tab.getText());
                viewPager2.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        ViewPager2.OnPageChangeCallback callback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
                super.onPageSelected(position);
            }
        };
        viewPager2.registerOnPageChangeCallback(callback);
    }
}