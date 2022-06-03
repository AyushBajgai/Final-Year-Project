package com.example.finalyearproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class usefulTipsFragment extends Fragment {

    private View rootView;

    private TextView textChange;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_useful_tips, container, false);

        ((dashboard)getActivity()).changeTitle("Tips for Public Speaking");

        return rootView;
    }
}