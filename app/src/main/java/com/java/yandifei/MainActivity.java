package com.java.yandifei;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.java.yandifei.network.NewsEntry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewsEntry.setActivity(this);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        final ViewPager2 viewPager2 = findViewById(R.id.nav_host_fragment);
        viewPager2.setAdapter(new HostFragmentAdapter(this));
        viewPager2.setUserInputEnabled(false);

        final BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                switch(item.getItemId()){
                    case R.id.navigation_news:
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.navigation_corona:
                        viewPager2.setCurrentItem(1);
                        break;
                }
                return true;
            }
        });

        ViewPager2.OnPageChangeCallback callback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch(position){
                case 0:
                    bottomNavigationView.setSelectedItemId(R.id.navigation_news);
                    break;
                case 1:
                    bottomNavigationView.setSelectedItemId(R.id.navigation_corona);
                    break;
                }
                super.onPageSelected(position);
            }
        };
        viewPager2.registerOnPageChangeCallback(callback);
    }

}