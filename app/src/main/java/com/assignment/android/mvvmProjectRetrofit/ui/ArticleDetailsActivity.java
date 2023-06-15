package com.assignment.android.mvvmProjectRetrofit.ui;

import static com.assignment.android.mvvmProjectRetrofit.Constants.BUNDLE_ARTICLE;
import static com.assignment.android.mvvmProjectRetrofit.Constants.showInfoAlertDialogNoIcon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.assignment.android.mvvmProjectRetrofit.ArticleAdapter.ArticleAdapter;
import com.assignment.android.mvvmProjectRetrofit.Constants;
import com.assignment.android.mvvmProjectRetrofit.R;
import com.assignment.android.mvvmProjectRetrofit.ReponseModel.Article;
import com.assignment.android.mvvmProjectRetrofit.ReponseModel.Source;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.security.DigestException;

public class ArticleDetailsActivity extends AppCompatActivity {

    private ImageView ivArticleImage;
    private TextView tvSourceName;
    private TextView tvAuthor;
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvUrl;
    private TextView tvPublishedAt;
    private TextView tvContent;
    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        initializeVariables();
        readBundle();
        bindUI();

    }

    private void initializeVariables()
    {
        article = new Article();
        ivArticleImage = findViewById(R.id.ivArticleImage);
        tvSourceName = findViewById(R.id.tvSourceName);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvUrl = findViewById(R.id.tvUrl);
        tvPublishedAt = findViewById(R.id.tvPublishedAt);
        tvContent = findViewById(R.id.tvContent);
    }

    private void readBundle(){
        if (getIntent().getExtras() != null) {
            article = (Article) Constants.getBundleObject(getIntent().getExtras(), Article.class, BUNDLE_ARTICLE);
        }
    }


    private void bindUI()
    {
        Source source = article.getSource();
        if (source != null) {
            tvSourceName.setText(source.getName());
        }
        tvAuthor.setText(article.getAuthor());
        tvTitle.setText(article.getTitle());
        tvDescription.setText(article.getDescription());
        tvUrl.setText(article.getUrl());
        tvPublishedAt.setText(article.getPublishedAt());
        tvContent.setText(article.getContent());

        // Load the article image
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
        builder.build().load(article.getUrlToImage()).placeholder((R.drawable.ic_launcher_background)).error(R.drawable.ic_launcher_background).into(ivArticleImage);
    }


}