package com.java.yandifei.ui.news;

import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.yandifei.R;
import com.java.yandifei.network.NewsEntry;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class NewsListFragment extends Fragment {

    public List<NewsEntry> newsList;
    public Fragment currentNewsList;
    public CharSequence tag = null;
    public NewsItemRecyclerViewAdapter adapter = null;

    public NewsListFragment(){
        this.newsList = new ArrayList<>();
    }

    public NewsListFragment(CharSequence tag) {
        this.tag = tag;
        this.newsList = new ArrayList<>();
    }

    public NewsListFragment(final List<NewsEntry> newsList) {
        this.newsList = newsList;
    }

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

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
                intent.setClass(getActivity(),NewsContentActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        if(tag!=null) {
            // get data from network
            new AsyncTask<String, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(String... strings) {
                    NewsEntry.getNewsList(tag, 1,
                            NewsItemRecyclerViewAdapter.entryNumPerPage, newsList);
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean success) {
                    adapter.notifyDataSetChanged();
                }
            }.execute();

            // Set up refresher and footer
            final RefreshLayout refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(final RefreshLayout refreshlayout) {
                    Toast.makeText(getContext(), "Refreshing ...", Toast.LENGTH_LONG).show();
                    new AsyncTask<String, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(String... strings) {
                            newsList.clear();
                            NewsEntry.getNewsList(tag, 1,
                                    NewsItemRecyclerViewAdapter.entryNumPerPage, newsList);
                            return true;
                        }

                        @Override
                        protected void onPostExecute(Boolean success) {
                            adapter.notifyDataSetChanged();
                            refreshlayout.finishRefresh();//传入false表示刷新失败
                        }
                    }.execute();
                }
            });
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(final RefreshLayout refreshlayout) {
                    Toast.makeText(getContext(), "Loading more ...", Toast.LENGTH_LONG).show();
                    new AsyncTask<String, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(String... strings) {
                            NewsEntry.getNewsList(tag, adapter.nextPageNum(),
                                    NewsItemRecyclerViewAdapter.entryNumPerPage, newsList);
                            return true;
                        }

                        @Override
                        protected void onPostExecute(Boolean success) {
                            adapter.notifyItemRangeInserted(adapter.lastInsertPos(),
                                    NewsItemRecyclerViewAdapter.entryNumPerPage);
                            refreshlayout.finishLoadMore();//传入false表示加载失败
                        }
                    }.execute();
                }
            });
        }
        return view;
    }

    private void switchNewsFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            if (currentNewsList != null) {
                transaction.hide(currentNewsList);
            }
            transaction.add(R.id.news_list, targetFragment);
        } else {
            transaction.hide(currentNewsList).show(targetFragment);
        }
        transaction.commit();
    }

}
