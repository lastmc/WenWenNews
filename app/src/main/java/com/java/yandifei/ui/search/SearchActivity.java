package com.java.yandifei.ui.search;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.java.yandifei.R;
import com.java.yandifei.network.NewsEntry;
import com.java.yandifei.ui.news.NewsListFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private NewsListFragment newsListFragment;
    private static SearchAsyncTask currentTask;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.search_tool_bar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final SearchView searchView = findViewById(R.id.search_view);
        final AutoCompleteTextView textView = searchView.findViewById(R.id.search_src_text);
        textView.setThreshold(0);
        String[] history = {"123","234"};
        final ArrayAdapter<String> historyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,history);
        textView.setAdapter(historyAdapter);
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = historyAdapter.getItem(position);
                textView.setText(str);
                searchView.clearFocus();
                submitSearch(str);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                submitSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        newsListFragment = new NewsListFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.search_result,newsListFragment);
        ft.commit();
    }

    private void submitSearch(final String key){
        if (currentTask != null) currentTask.cancel(false);
        currentTask = new SearchAsyncTask(key,newsListFragment,1,300);
        newsListFragment.newsList.clear();
        currentTask.execute();
    }

    private static class SearchAsyncTask extends AsyncTask<String,Void,Boolean>{
        int curK;
        int maxK;
        String key;
        NewsListFragment newsListFragment;
        private List<NewsEntry> raw = new ArrayList<>();

        SearchAsyncTask(String key,NewsListFragment newsListFragment,int curK,int maxK){
            this.key = key;
            this.newsListFragment = newsListFragment;
            this.curK = curK;
            this.maxK = maxK;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            NewsEntry.getNewsList("all", curK, 200, raw);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            List<NewsEntry> result = newsListFragment.newsList;
            int start = result.size();
            for (NewsEntry e : raw) {
                if (e.title.toLowerCase().contains(key.toLowerCase())) {
                    result.add(e);
                }
            }
            updateItems(start, result.size());
            if(curK<maxK) {
                currentTask = new SearchAsyncTask(key,newsListFragment,curK+1,maxK);
                currentTask.execute();
            }
        }

        public void updateItems(int start, int end) {
            while (start++ < end)
                newsListFragment.adapter.notifyItemInserted(start);
        }
    }
}
