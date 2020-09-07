package com.java.yandifei.ui.tab;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayout;
import com.java.yandifei.R;
import com.java.yandifei.network.MyApplication;
import com.java.yandifei.ui.news.NewsTabViewPagerAdapter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TabSettingActivity extends AppCompatActivity {

    ArrayList<CharSequence> selectedTabs;
    ArrayList<CharSequence> unselectedTabs;
    public static String SELECTED = "selected";
    public static String UNSELECTED = "unselected";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_setting);

        // get data from intent
        selectedTabs = getIntent().getCharSequenceArrayListExtra("selected");
        unselectedTabs = getIntent().getCharSequenceArrayListExtra("unselected");

        final ChipGroup selectedTabsView = findViewById(R.id.selected_tabs);
        final ChipGroup unselectedTabsView = findViewById(R.id.unselected_tabs);
        LayoutTransition myAnimation = new LayoutTransition();
        myAnimation.setAnimator(LayoutTransition.DISAPPEARING, null);
        myAnimation.setAnimator(LayoutTransition.APPEARING, null);
        selectedTabsView.setLayoutTransition(myAnimation);
        unselectedTabsView.setLayoutTransition(myAnimation);
        addChipsToGroup(selectedTabsView, selectedTabs, selectedTabsView, unselectedTabsView);
        addChipsToGroup(unselectedTabsView, unselectedTabs, selectedTabsView, unselectedTabsView);

        // set toolbar back button
        Toolbar toolbar = findViewById(R.id.tab_setting_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void addChipsToGroup(final ChipGroup group, List<CharSequence> chipNames,
            final ChipGroup selectedTabsView, final ChipGroup unselectedTabsView) {
        final Chip holderChip = new Chip(group.getContext());
        holderChip.setVisibility(View.INVISIBLE);
        for (CharSequence tab : chipNames) {
            final Chip newChip = new Chip(selectedTabsView.getContext());
            newChip.setText(tab);
            newChip.setElevation(10);
            if (tab.equals("all")) newChip.setEnabled(false);
            newChip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final ChipGroup currentGroup, toGroup;
                    CharSequence tabName = ((Chip)v).getText();
                    if (v.getParent() == (ViewParent) selectedTabsView) {
                        currentGroup = selectedTabsView;
                        toGroup = unselectedTabsView;
                        selectedTabs.remove(tabName);
                        unselectedTabs.add(tabName);
                    } else {
                        currentGroup = unselectedTabsView;
                        toGroup = selectedTabsView;
                        unselectedTabs.remove(tabName);
                        selectedTabs.add(tabName);
                    }
                    toGroup.addView(holderChip);
                    toGroup.post(new Runnable() {
                        @Override
                        public void run() {
                            int originPos[] = new int[2];
                            v.getLocationOnScreen(originPos);
                            int toPos[] = new int[2];
                            holderChip.getLocationOnScreen(toPos);
                            toGroup.removeView(holderChip);
                            TranslateAnimation animation = new TranslateAnimation(0,
                                    toPos[0] - originPos[0], 0,
                                    toPos[1] - originPos[1]);
                            animation.setRepeatMode(0);
                            animation.setDuration(500);
                            //animation.setFillAfter(true);
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    v.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    v.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            v.startAnimation(animation);
                            currentGroup.removeView(v);
                            v.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toGroup.addView(v);
                                }
                            }, 550);
                        }
                    });
                }
            });
            group.addView(newChip);
        }
    }


    public void setReturnData() {
        Intent returnIntent = new Intent();
        returnIntent.putCharSequenceArrayListExtra("selected", selectedTabs);
        returnIntent.putCharSequenceArrayListExtra("unselected", unselectedTabs);
        saveTabs(selectedTabs, unselectedTabs);
        setResult(0x5462, returnIntent);
    }

    @Override
    public void onBackPressed() {
        setReturnData();
        finish();
        super.onBackPressed();
    }

    public void saveTabs(List<CharSequence> selected, List<CharSequence> unselected) {
        MyApplication globalVariables = (MyApplication)getApplication();
        SharedPreferences storage = this.getSharedPreferences(globalVariables.tabsListTableName, MODE_PRIVATE);
        SharedPreferences.Editor editor = storage.edit();
        // save selected tabs
        Set<String> stringSetToSave = new LinkedHashSet<String>();
        for (CharSequence a : selected) stringSetToSave.add(a.toString());
        editor.putStringSet(TabSettingActivity.SELECTED, stringSetToSave);
        editor.apply();
        // save unselected tabs
        stringSetToSave.clear();
        for (CharSequence a : unselected) stringSetToSave.add(a.toString());
        editor.putStringSet(TabSettingActivity.UNSELECTED, stringSetToSave);
        // commit
        editor.apply();
    }

    public static void getTabs(Activity activity, List<CharSequence> selected, List<CharSequence> unselected) {
        MyApplication globalVariables = (MyApplication)activity.getApplication();
        SharedPreferences storage = activity.getSharedPreferences(globalVariables.tabsListTableName, MODE_PRIVATE);
        // get selected tabs
        Set<String> stringSetLoaded = storage.getStringSet(TabSettingActivity.SELECTED, null);
        selected.clear();
        if (stringSetLoaded == null) {
            selected.add("all");
            selected.add("news");
            selected.add("paper");
        } else selected.addAll(stringSetLoaded);
        // get unselected tabs
        stringSetLoaded = storage.getStringSet(TabSettingActivity.UNSELECTED, null);
        unselected.clear();
        if (stringSetLoaded != null) unselected.addAll(stringSetLoaded);
    }
}
