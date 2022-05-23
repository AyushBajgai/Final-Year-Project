package com.example.finalyearproject.Models;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {

    //fetching the data as list of headlines
    void onFetchData(List<NewsHeadlines> list, String message);

    //for handling errors
    void onError(String message);
}
