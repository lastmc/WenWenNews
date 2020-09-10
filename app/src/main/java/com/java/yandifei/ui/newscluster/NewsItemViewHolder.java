package com.java.yandifei.ui.newscluster;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yandifei.R;

public class NewsItemViewHolder extends RecyclerView.ViewHolder {
    public TextView newsTitle;
    public TextView newsDescription;

    public NewsItemViewHolder(@NonNull View itemView) {
        super(itemView);
        newsTitle = itemView.findViewById(R.id.news_item_title);
        newsDescription = itemView.findViewById(R.id.news_item_description);
    }

    public void setAllTextColor(int color) {
        newsTitle.setTextColor(color);
        newsDescription.setTextColor(color);
    }
}
