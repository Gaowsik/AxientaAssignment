package com.assignment.android.mvvmProjectRetrofit.Retrofit;

import com.assignment.android.mvvmProjectRetrofit.ReponseModel.NewsResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface  GetDataService {

//    @GET("/photos")
//    Call<List<RetroPhoto>> getAllPhotos();
@GET("everything")
Call<NewsResponse> getNews(@Query("q") String query, @Query("apiKey") String apiKey);


}
