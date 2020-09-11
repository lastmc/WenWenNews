package com.java.yandifei.ui.scholar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yandifei.R;
import com.java.yandifei.network.Scholar;

import java.util.List;

public class ScholarItemRecyclerViewAdapter extends RecyclerView.Adapter<ScholarItemViewHolder> implements View.OnClickListener {
    private List<Scholar> scholarList;
    private OnItemClickListener onItemClickListener;

    ScholarItemRecyclerViewAdapter(List<Scholar> scholarList){
        this.scholarList = scholarList;
    }

    @NonNull
    @Override
    public ScholarItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_scholar_item, parent, false);
        ScholarItemViewHolder viewHolder = new ScholarItemViewHolder(layoutView);
        layoutView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScholarItemViewHolder holder, int position) {
        if (scholarList != null && position < scholarList.size()) {
            Scholar scholar = scholarList.get(position);
            String name = scholar.nameString();
            if (scholar.is_passedaway) name += "（追忆）";
            holder.scholarName.setText(name);
            holder.scholarPosition.setText(scholar.profile.position);
            holder.itemView.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        return scholarList.size();
    }

    @Override
    public void onClick(View view) {
        Scholar clickedNews = scholarList.get((int)view.getTag());
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
        return scholarList.get(position).id.hashCode();
    }
}
