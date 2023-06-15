package com.assignment.android.mvvmProjectRetrofit.SQLDB;

import android.content.Context;

import com.assignment.android.mvvmProjectRetrofit.ReponseModel.Article;

import java.util.List;

public class NewsDataSource {
    private DBHelper dbHelper;

    public NewsDataSource(Context applicationContext) {
        dbHelper = new DBHelper(applicationContext);
    }

    public void insertArticle(Article article) {
        dbHelper.insertArticle(article);
    }

    public List<Article> getAllArticles() {
        return dbHelper.getAllArticles();
    }

    public boolean deleteArticle(Article article) {
        return dbHelper.deleteArticle(article);
    }
}
