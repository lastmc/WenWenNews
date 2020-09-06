package com.java.yandifei.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.java.yandifei.R;

import java.util.List;

public class TabSettingFragment extends Fragment {

    List<? extends CharSequence> selectedTabs;

    public TabSettingFragment(List<? extends CharSequence> selectedTabs) {
        this.selectedTabs = selectedTabs;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_setting, container, false);
        final ChipGroup selectedTabsView = view.findViewById(R.id.selected_tabs);
        final ChipGroup unselectedTabsView = view.findViewById(R.id.unselected_tabs);
        // last of selected tabs is tab setting
        for (CharSequence tab : selectedTabs.subList(0, selectedTabs.size() - 1)) {
            final Chip newChip = new Chip(selectedTabsView.getContext());
            newChip.setText(tab);
            newChip.setElevation(10);
            if (selectedTabs.indexOf(tab) == 0) newChip.setEnabled(false);
            newChip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final ChipGroup currentGroup, toGroup;
                    if (v.getParent() == (ViewParent)selectedTabsView) {
                        currentGroup = selectedTabsView;
                        toGroup = unselectedTabsView;
                    } else {
                        currentGroup = unselectedTabsView;
                        toGroup = selectedTabsView;
                    }
                    float originX = v.getX();
                    float originY = v.getY();
                    View lastChildOfToGroup = toGroup.getChildAt(toGroup.getChildCount() - 1);
                    if (lastChildOfToGroup == null) lastChildOfToGroup = toGroup;
                    float toX = lastChildOfToGroup.getX();
                    float toY = lastChildOfToGroup.getY();
                    TranslateAnimation animation = new TranslateAnimation(0,
                            toX - originX, 0,
                            toY - originY);
                    animation.setRepeatMode(0);
                    animation.setDuration(1000);
                    animation.setFillAfter(false);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            currentGroup.removeView(v);
                            toGroup.addView(v);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    v.startAnimation(animation);
                }
            });
            selectedTabsView.addView(newChip);
        }
        return view;
    }
}
