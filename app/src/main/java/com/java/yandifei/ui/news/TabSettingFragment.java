package com.java.yandifei.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        ChipGroup selectedTabsView = view.findViewById(R.id.selected_tabs);
        // last of selected tabs is tab setting
        for (CharSequence tab : selectedTabs.subList(0, selectedTabs.size() - 1)) {
            Chip newChip = new Chip(selectedTabsView.getContext());
            newChip.setText(tab);
            newChip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TranslateAnimation animation = new TranslateAnimation()
                }
            });
            selectedTabsView.addView(newChip);
        }
        return view;
    }
}
