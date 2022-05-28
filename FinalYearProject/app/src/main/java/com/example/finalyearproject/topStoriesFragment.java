package com.example.finalyearproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finalyearproject.Models.CustomAdapter;
import com.example.finalyearproject.Models.NewsApiResponse;
import com.example.finalyearproject.Models.NewsHeadlines;
import com.example.finalyearproject.Models.OnFetchDataListener;
import com.example.finalyearproject.Models.RequestManager;

import java.util.List;

public class topStoriesFragment extends Fragment {

    CustomAdapter customAdapter;
    RecyclerView recyclerView;

    private View rootView2;


    private TextView textChange;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView2 = inflater.inflate(R.layout.fragment_top_stories, container, false);

        ((dashboard)getActivity()).changeTitle("Top Stories");

        RequestManager requestManager = new RequestManager();
        requestManager.getNewsHeadlines(listener, "general", null);
        return rootView2;
    }
    private final OnFetchDataListener<NewsApiResponse> listener= new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            showNews(list);
        }

        @Override
        public void onError(String message) {

        }
    };

    private void showNews(List<NewsHeadlines> list){
       RecyclerView recyclerView = (RecyclerView) rootView2.findViewById(R.id.recycler_report);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

       RecyclerView.Adapter adapter = new CustomAdapter(getActivity(), list);
       recyclerView.setAdapter(adapter);
    }

}