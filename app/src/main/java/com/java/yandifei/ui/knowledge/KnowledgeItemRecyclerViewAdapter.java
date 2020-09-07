package com.java.yandifei.ui.knowledge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yandifei.R;
import com.java.yandifei.network.KnowledgeGraph;

import java.util.List;

public class KnowledgeItemRecyclerViewAdapter extends RecyclerView.Adapter<KnowledgeItemViewHolder> implements View.OnClickListener {
    private List<KnowledgeGraph> entityList;
    private OnItemClickListener onItemClickListener;

    KnowledgeItemRecyclerViewAdapter(List<KnowledgeGraph> entityList){
        this.entityList = entityList;
    }

    @NonNull
    @Override
    public KnowledgeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_entity_item, parent, false);
        KnowledgeItemViewHolder viewHolder = new KnowledgeItemViewHolder(layoutView);
        layoutView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KnowledgeItemViewHolder holder, int position) {
        if (entityList != null && position < entityList.size()) {
            KnowledgeGraph entity = entityList.get(position);
            holder.entityName.setText(entity.label);
            holder.entityBrief.setText(entity.getBrief());
            holder.itemView.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }

    @Override
    public void onClick(View view) {
        KnowledgeGraph clickedNews = entityList.get((int)view.getTag());
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, (int)view.getTag());
        }
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
        return entityList.get(position).label.hashCode();
    }
}
