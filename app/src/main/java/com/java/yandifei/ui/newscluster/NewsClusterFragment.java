package com.java.yandifei.ui.newscluster;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.java.yandifei.R;
import com.java.yandifei.ui.search.SearchActivity;
import com.java.yandifei.ui.tab.TabSettingActivity;

import java.util.ArrayList;
import java.util.List;

public class NewsClusterFragment extends Fragment {

    ArrayList<CharSequence> unselectedList = new ArrayList<CharSequence>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        List<CharSequence> list = new ArrayList<>();
        TabSettingActivity.getTabs(getActivity(), list, unselectedList);
        bindTabWithViewPager(view,list);

        setHasOptionsMenu(false);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        menuInflater.inflate(R.menu.tool_bar_menu,menu);
        super.onCreateOptionsMenu(menu,menuInflater);
    }

    private void bindTabWithViewPager(View view,final List<CharSequence> list){
        final TabLayout tabLayout = view.findViewById(R.id.news_tabs);
        final ViewPager2 viewPager2 = view.findViewById(R.id.news_list_container);
        final NewsClusterTabViewPagerAdapter adapter = new NewsClusterTabViewPagerAdapter(list,getActivity());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x5438 && resultCode == 0x5462) {
            List<CharSequence> unselected = data.getCharSequenceArrayListExtra("unselected");
            final ViewPager2 viewPager2 = getView().findViewById(R.id.news_list_container);
            // update tabs
            NewsClusterTabViewPagerAdapter adapter = (NewsClusterTabViewPagerAdapter)viewPager2.getAdapter();
            adapter.list.clear();
            adapter.list.addAll(data.getCharSequenceArrayListExtra("selected"));
            adapter.notifyDataSetChanged();
            final TabLayout tabLayout = getView().findViewById(R.id.news_tabs);
            tabLayout.removeAllTabs();
            for (CharSequence tab : adapter.list) tabLayout.addTab(tabLayout.newTab().setText(tab));

            // update unselected tabs
            unselectedList.clear();
            unselectedList.addAll(data.getCharSequenceArrayListExtra("unselected"));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
