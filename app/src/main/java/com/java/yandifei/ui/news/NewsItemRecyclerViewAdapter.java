package com.java.yandifei.ui.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yandifei.R;
import com.java.yandifei.network.NewsEntry;

import java.util.List;

public class NewsItemRecyclerViewAdapter extends RecyclerView.Adapter<NewsItemViewHolder> implements View.OnClickListener {
    private List<NewsEntry> newsList;
    private OnItemClickListener onItemClickListener;

    NewsItemRecyclerViewAdapter(List<NewsEntry> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news_item, parent, false);
        NewsItemViewHolder viewHolder = new NewsItemViewHolder(layoutView);
        layoutView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder holder, int position) {
        if (newsList != null && position < newsList.size()) {
            NewsEntry news = newsList.get(position);
            System.out.println("mylog " + news);
            holder.newsTitle.setText(news.title);
            holder.newsDescription.setText(news.description);
            holder.itemView.setTag(position);
            System.out.println(news);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, (int)view.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
