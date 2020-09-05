package com.java.yandifei.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yandifei.R;
import com.java.yandifei.network.NewsEntry;

import java.util.List;

public class NewsListFragment extends Fragment {

    private List<NewsEntry> newsList;

    public NewsListFragment(final List<NewsEntry> newsList) {
        this.newsList = newsList;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        // Set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.news_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
        NewsItemRecyclerViewAdapter adapter = new NewsItemRecyclerViewAdapter(this.newsList);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
