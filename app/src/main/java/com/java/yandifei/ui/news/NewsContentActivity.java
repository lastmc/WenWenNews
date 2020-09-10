package com.java.yandifei.ui.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.java.yandifei.R;
import com.java.yandifei.network.NewsEntry;

public class NewsContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        Toolbar toolbar = findViewById(R.id.news_content_tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadNewsContent((NewsEntry) getIntent().getSerializableExtra("data"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        new MenuInflater(this).inflate(R.menu.share_menu,menu);
        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.menu_share){
            TextView title = findViewById(R.id.news_title);
            TextView content = findViewById(R.id.news_content);
            String newsBrief = title.getText() + "\n简介：" + content.getText().subSequence(0,20) + "......\n---------------------\nFrom WenWenNews";
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,newsBrief);
            intent.setType("text/plain");
            startActivity(intent);
        }
        return true;
    }

    private void loadNewsContent(NewsEntry entry){
        System.out.println(entry);
        TextView title = findViewById(R.id.news_title);
        TextView content = findViewById(R.id.news_content);
        TextView author = findViewById(R.id.news_author);
        title.setText(entry.title);
        content.setText(entry.content);//Provide html support?
        author.setText(entry.source+" · "+entry.time);
    }
}
