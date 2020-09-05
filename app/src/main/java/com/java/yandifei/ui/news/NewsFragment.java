package com.java.yandifei.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.java.yandifei.R;
import com.java.yandifei.network.NewsEntry;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    private Fragment currentNewsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");

        bindTabWithViewPager(view,list);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        menuInflater.inflate(R.menu.tool_bar_menu,menu);
        super.onCreateOptionsMenu(menu,menuInflater);
    }


    private void bindTabWithViewPager(View view,final List<? extends CharSequence> list){
        final TabLayout tabLayout = view.findViewById(R.id.news_tabs);
        final ViewPager2 viewPager2 = view.findViewById(R.id.news_container);
        final NewsTabViewPagerAdapter adapter = new NewsTabViewPagerAdapter(list,getActivity());
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
