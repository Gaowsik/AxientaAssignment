package com.assignment.android.mvvmProjectRetrofit;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.android.mvvmProjectRetrofit.ReponseModel.Article;
import com.assignment.android.mvvmProjectRetrofit.ReponseModel.NewsResponse;

import java.util.List;

public class MainViewModel extends ViewModel {
    Repository repository;
    public MutableLiveData<List<Article>> articleLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isDeleted = new MutableLiveData<>();


    public MainViewModel(Context context, RecyclerView recyclerView) {
        repository = new Repository(context, recyclerView);
    }

    public MutableLiveData<NewsResponse> getTasks(String query, String apiKey) {
        return repository.getTasks(query, apiKey);
    }


    public void insertDataToDb(List<Article> articles) {
        repository.insertDataToDb(articles);
        articleLiveData.setValue(repository.getAllDataFromDB());


    }

    public void deleteDataToDb(Article article) {
        if(repository.deleteDataFromDB(article)){
            isDeleted.setValue(true);
            articleLiveData.setValue(repository.getAllDataFromDB());
        }

        else
        {
            isDeleted.setValue(false);
        }



    }


}
