package com.example.newsapp.data.remote;

import com.example.newsapp.data.models.MainResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("top-headlines")
    Call<MainResponse> getTopNews(
            @Query("country") String country,
            @Query("apiKey") String apikey
    );

    @GET("top-headlines")
    Call<MainResponse> getNewsByCategory(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apikey
    );

    @GET("everything")
    Call<MainResponse> getNewsByKeyWord(
            @Query("q") String keyWord,
            @Query("apiKey") String apikey
    );
}
