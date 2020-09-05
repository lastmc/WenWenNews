package com.java.yandifei.ui.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yandifei.R;
import com.java.yandifei.network.NewsEntry;

import java.util.List;

import static java.lang.Math.min;

public class NewsItemRecyclerViewAdapter extends RecyclerView.Adapter<NewsItemViewHolder> implements View.OnClickListener {
    private List<NewsEntry> newsList;
    private OnItemClickListener onItemClickListener;
    private int pageNum;
    public static final int entryNumPerPage = 20;

    NewsItemRecyclerViewAdapter(List<NewsEntry> newsList) {
        this.newsList = newsList;
        this.pageNum = 1;
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
        if (newsList != null && position < newsList.size() && position < pageNum*entryNumPerPage) {
            NewsEntry news = newsList.get(position);
            holder.newsTitle.setText(news.title);
            holder.newsDescription.setText(news.description);
            holder.itemView.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        return min(newsList.size(), pageNum*entryNumPerPage);
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    public void loadMore() {
        pageNum += 1;
        notifyDataSetChanged();
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
