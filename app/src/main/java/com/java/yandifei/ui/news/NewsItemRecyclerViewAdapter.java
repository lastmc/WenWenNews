package com.java.yandifei.ui.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yandifei.R;
import com.java.yandifei.network.NewsEntry;

import java.util.List;

public class NewsItemRecyclerViewAdapter extends RecyclerView.Adapter<NewsItemViewHolder> {
    private List<NewsEntry> newsList;

    NewsItemRecyclerViewAdapter(List<NewsEntry> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news_item, parent, false);
        return new NewsItemViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder holder, int position) {
        if (newsList != null && position < newsList.size()) {
            NewsEntry news = newsList.get(position);
            System.out.println("mylog " + news);
            holder.newsTitle.setText(news.title);
            holder.newsDescription.setText(news.description);
            System.out.println(news);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
