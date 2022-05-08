package com.example.finalyearproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class topStoriesFragment extends Fragment {

    private View rootView2;

    private TextView textChange;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView2 = inflater.inflate(R.layout.fragment_top_stories, container, false);

        ((dashboard)getActivity()).changeTitle("Top Stories");
        return rootView2;
    }
}