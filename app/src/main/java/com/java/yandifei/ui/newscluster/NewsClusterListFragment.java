package com.java.yandifei.ui.newscluster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.java.yandifei.R;
import com.java.yandifei.network.NewsEntry;
import com.java.yandifei.ui.news.NewsContentActivity;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NewsClusterListFragment extends Fragment {

    public List<NewsEntry> newsList;
    public Fragment currentNewsList;
    public CharSequence tag = null;
    public NewsItemRecyclerViewAdapter adapter = null;

    public NewsClusterListFragment(){}

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        this.tag = getArguments().getCharSequence("tag");
        int theme = getArguments().getInt("theme");
        this.newsList = new ArrayList<>();

        // get data from json
        InputStream data = getResources().openRawResource(R.raw.classes);
        try {
            JSONArray dataArray = JSON.parseObject(data, JSONArray.class);
            for (int i = 0; i < dataArray.size(); ++i) {
                NewsEntry entry = JSON.parseObject(dataArray.getString(i), NewsEntry.class);
                entry.tag = this.tag.toString();
                if (entry.theme.equals(String.valueOf(theme))) {
                    newsList.add(entry);
                }
            }
        } catch (Exception e) {
            Log.d("mylog", e.toString());
        }

        // Set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.news_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
        final NewsItemRecyclerViewAdapter adapter = new NewsItemRecyclerViewAdapter(this.newsList,
                this.tag, getActivity());
        this.adapter = adapter;
        adapter.setOnItemClickListener(new NewsItemRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("data", newsList.get(position));
                intent.setClass(getActivity(), NewsContentActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        // Set up refresher and footer
        final RefreshLayout refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshlayout) {
                adapter.nextPageNum();
                adapter.notifyDataSetChanged();
                refreshlayout.finishLoadMore();//传入false表示加载失败
            }
        });
        return view;
    }

}
