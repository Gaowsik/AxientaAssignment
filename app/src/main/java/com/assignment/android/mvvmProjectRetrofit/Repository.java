package com.assignment.android.mvvmProjectRetrofit;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.android.mvvmProjectRetrofit.ReponseModel.Article;
import com.assignment.android.mvvmProjectRetrofit.ReponseModel.NewsResponse;
import com.assignment.android.mvvmProjectRetrofit.Retrofit.GetDataService;
import com.assignment.android.mvvmProjectRetrofit.Retrofit.RetrofitClientInstance;
import com.assignment.android.mvvmProjectRetrofit.SQLDB.DBHelper;
import com.assignment.android.mvvmProjectRetrofit.SQLDB.NewsDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    Context context;
    RecyclerView recyclerView;
    private GetDataService service;
    private NewsDataSource dataSource;

    public Repository(Context applicationContext, RecyclerView recyclerView) {
        context = applicationContext;
        this.recyclerView = recyclerView;
        dataSource = new NewsDataSource(applicationContext);
    }

    public MutableLiveData<NewsResponse> getTasks(String query, String apiKey) {
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<NewsResponse> call = service.getNews(query, apiKey);

        final MutableLiveData<NewsResponse> newsData = new MutableLiveData<>();
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                newsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                System.out.println("onFailure");
                newsData.setValue(null);
            }
        });
        return newsData;
    }


    public void insertDataToDb(List<Article> articles) {
        final MutableLiveData<List<Article>> articlesLiveData = new MutableLiveData<>();
        for (Article article : articles) {
            dataSource.insertArticle(article);
        }

    }

    public List<Article> getAllDataFromDB() {
        List<Article> allData = dataSource.getAllArticles();
        return allData;

    }

    public Boolean deleteDataFromDB(Article article) {
        boolean isDeleted = false;
        if (dataSource.deleteArticle(article)) {
            isDeleted = true;
        } else {
            isDeleted = false;
        }
        return isDeleted;

    }


}
