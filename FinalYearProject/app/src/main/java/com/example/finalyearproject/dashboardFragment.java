package com.example.finalyearproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalyearproject.databinding.FragmentDashboardBinding;

public class dashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentDashboardBinding.inflate(inflater, container, false);

        //Initiating Dashboard title permanent
        ((dashboard)getActivity()).changeTitle("Dashboard");

        //While Clicking on Analyze Speech load another fragment
        binding.cardAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             getParentFragmentManager().beginTransaction().replace(R.id.fragment_view,new analyzeSpeechFragment()).addToBackStack(null).commit();
            }
        });

        //While Clicking on Useful tips load useful tips fragment
        binding.cardTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_view, new usefulTipsFragment()).addToBackStack(null).commit();
            }
        });

        //While Clicking on TopStories load top stories fragment
        binding.cardNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_view, new topStoriesFragment()).addToBackStack(null).commit();
            }
        });

        //While Clicking on viewReports load top view report fragment
        binding.cardReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_view, new view_report()).addToBackStack(null).commit();
            }
        });

        return binding.getRoot();
    }
}