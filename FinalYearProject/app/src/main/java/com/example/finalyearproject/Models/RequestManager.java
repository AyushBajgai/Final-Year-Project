package com.example.finalyearproject.Models;

import android.content.Context;
import android.widget.Toast;

import com.example.finalyearproject.topStoriesFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {

    Context context;
    //API key
    String api = "aac4fb9367c34dbf97bf94cbb16963b9";

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://newsapi.org/v2/").addConverterFactory(GsonConverterFactory.create()).build();

    public void getNewsHeadlines(OnFetchDataListener listener, String category, String query) {

    //Starting API calling
    CallNewsAPI callNewsAPI = retrofit.create(CallNewsAPI.class);
    Call<NewsApiResponse> call = callNewsAPI.callHeadlines("us", category, query, api);

    //Calling API try block
    try {
        call.enqueue(new Callback<NewsApiResponse>() {
            @Override
            public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
            if(!response.isSuccessful()){
                Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
            }
            listener.onFetchData(response.body().getArticles(),response.message());
            }

            //In case of failure
            @Override
            public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                listener.onError("Request Failed");
            }
        });
    }catch (Exception e){
        e.printStackTrace();
    }

}
    //Calling the API via queries
    public interface CallNewsAPI{
        @GET("top-headlines")
        Call<NewsApiResponse> callHeadlines(
                //Passing the queries
                @Query("country") String country,
                @Query("category") String category,
                @Query("q") String query,
                @Query("apiKey") String api_key

        );
    }
}
