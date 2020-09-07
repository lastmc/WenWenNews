package com.java.yandifei.ui.news;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yandifei.R;
import com.java.yandifei.network.NewsEntry;

import java.util.Collection;
import java.util.List;

import static java.lang.Math.min;

public class NewsItemRecyclerViewAdapter extends RecyclerView.Adapter<NewsItemViewHolder> implements View.OnClickListener {
    private List<NewsEntry> newsList;
    private OnItemClickListener onItemClickListener;
    private int pageNum;
    private final CharSequence tag;
    private final FragmentActivity activity;
    public static final int entryNumPerPage = 20;

    NewsItemRecyclerViewAdapter(List<NewsEntry> newsList, CharSequence tag, FragmentActivity activity) {
        this.newsList = newsList;
        this.tag = tag;
        this.activity = activity;
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
        if (newsList != null && position < newsList.size() && (position < pageNum*entryNumPerPage || tag == null)) {
            NewsEntry news = newsList.get(position);
            if(news == null) System.out.println("MYLOG NULL");
            holder.newsTitle.setText(news.title);
            holder.newsDescription.setText(news.source + " | " + news.time);
            holder.itemView.setTag(position);
            if (NewsEntry.newsIsRead(news._id))
                holder.setAllTextColor(Color.rgb(102, 102, 102));
        }
    }

    @Override
    public int getItemCount() {
        if(tag == null) return newsList.size();
        return min(newsList.size(), pageNum*entryNumPerPage);
    }

    public int nextPageNum() {
        pageNum += 1;
        return pageNum;
    }

    @Override
    public void onClick(View view) {
        NewsEntry clickedNews = newsList.get((int)view.getTag());
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, (int)view.getTag());
        }
        NewsEntry.saveReadNewsState(clickedNews);
        notifyItemChanged((int)view.getTag());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public long getItemId(int position){
        return newsList.get(position)._id.hashCode();
    }
}
