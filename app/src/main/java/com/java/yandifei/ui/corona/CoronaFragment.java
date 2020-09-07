package com.java.yandifei.ui.corona;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.java.yandifei.R;

import java.util.ArrayList;
import java.util.List;

public class CoronaFragment extends Fragment {

    private CoronaViewModel coronaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        coronaViewModel =
                ViewModelProviders.of(this).get(CoronaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_corona, container, false);
        List<CharSequence> list = new ArrayList<>();
        list.add(getString(R.string.tab_corona_data));
        list.add(getString(R.string.tab_corona_figure));
        list.add(getString(R.string.tab_corona_news));
        list.add(getString(R.string.tab_corona_scholar));

        //bindTabWithViewPager(view,list);
        setHasOptionsMenu(true);
        return root;
    }
}