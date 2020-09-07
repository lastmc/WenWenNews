package com.java.yandifei.ui.coronadata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.java.yandifei.R;
import com.java.yandifei.network.CoronaData;
import com.java.yandifei.network.Scholar;

import java.util.ArrayList;
import java.util.List;

public class CoronaDataItemRecyclerViewAdapter extends RecyclerView.Adapter<CoronaDataItemViewHolder> {
    private List<CoronaData> coronaDataList;

    CoronaDataItemRecyclerViewAdapter(List<CoronaData> coronaDataList){
        this.coronaDataList = coronaDataList;
    }

    @NonNull
    @Override
    public CoronaDataItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fragment_corona_data_item, parent, false);
        CoronaDataItemViewHolder viewHolder = new CoronaDataItemViewHolder(layoutView);
        //layoutView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CoronaDataItemViewHolder holder, int position) {
        if (coronaDataList != null && position < coronaDataList.size()) {
            CoronaData coronaData = coronaDataList.get(position);
            holder.district.setText(coronaData.district);
            holder.beginTime.setText(coronaData.begin);
            // line chart data
            List<Entry> entries = new ArrayList<Entry>();
            for (int i = 0; i < coronaData.dataOfDays.size(); ++i)
                entries.add(new Entry(i, coronaData.dataOfDays.get(i).confirmed));
            LineDataSet dataSet = new LineDataSet(entries, "confirmed");
            holder.lineChart.setData(new LineData(dataSet));
            holder.lineChart.invalidate();
            holder.itemView.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        return coronaDataList.size();
    }

    //@Override
    //public void onClick(View view) {
    //    Scholar clickedNews = scholarList.get((int)view.getTag());
    //    if (onItemClickListener != null) {
    //        onItemClickListener.onItemClick(view, (int)view.getTag());
    //    }
    //    notifyItemChanged((int)view.getTag());
    //}

    //public void setOnItemClickListener(OnItemClickListener listener) {
    //    this.onItemClickListener = listener;
    //}

    //public interface OnItemClickListener {
    //    void onItemClick(View view, int position);
    //}

    @Override
    public long getItemId(int position){
        return coronaDataList.get(position).district.hashCode();
    }
}
