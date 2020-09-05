package com.java.yandifei.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yandifei.R;
import com.java.yandifei.network.NewsEntry;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    private Fragment currentNewsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        Fragment newsListFragment = new NewsListFragment(
                NewsEntry.initNewsEntryList(getResources())
        );
        switchNewsListFragment(newsListFragment);
        // Set up the RecyclerView
        //RecyclerView recyclerView = view.findViewById(R.id.news_list);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
        //NewsItemRecyclerViewAdapter adapter = new NewsItemRecyclerViewAdapter(
        //        NewsEntry.initNewsEntryList(getResources()));
        //recyclerView.setAdapter(adapter);
        //int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
        //int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);
        //recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));
        //final TextView textView = root.findViewById(R.id.text_home);
        //newsViewModel.getText().observe(this, new Observer<String>() {
        //    @Override
        //    public void onChanged(@Nullable String s) {
        //        textView.setText(s);
         //   }
        //});

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        menuInflater.inflate(R.menu.tool_bar_menu,menu);
        super.onCreateOptionsMenu(menu,menuInflater);
    }

    private void switchNewsListFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            if (currentNewsList != null) {
                transaction.hide(currentNewsList);
            }
            transaction.add(R.id.news_list, targetFragment);
        } else {
            transaction.hide(currentNewsList).show(targetFragment);
        }
        transaction.commit();
    }
}
