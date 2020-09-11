package com.java.yandifei.ui.news;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yandifei.R;

public class NewsItemViewHolder extends RecyclerView.ViewHolder {
    public TextView newsTitle;
    public TextView newsDescription;
    public int titleColor;
    public int desColor;

    public NewsItemViewHolder(@NonNull View itemView) {
        super(itemView);
        newsTitle = itemView.findViewById(R.id.news_item_title);
        newsDescription = itemView.findViewById(R.id.news_item_description);
        titleColor = newsTitle.getCurrentTextColor();
        desColor = newsDescription.getCurrentTextColor();
    }

    public void setAllTextColor(int color) {
        newsTitle.setTextColor(color);
        newsDescription.setTextColor(color);
    }

    public void resetTextColor() {
        newsTitle.setTextColor(titleColor);
        newsDescription.setTextColor(desColor);
    }
}
