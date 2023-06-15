package com.assignment.android.mvvmProjectRetrofit.ui;

import static com.assignment.android.mvvmProjectRetrofit.Constants.showInfoAlertDialogNoIcon;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.android.mvvmProjectRetrofit.ArticleAdapter.ArticleAdapter;
import com.assignment.android.mvvmProjectRetrofit.MainViewModel;
import com.assignment.android.mvvmProjectRetrofit.MainViewModelFactory;
import com.assignment.android.mvvmProjectRetrofit.R;
import com.assignment.android.mvvmProjectRetrofit.ReponseModel.Article;
import com.assignment.android.mvvmProjectRetrofit.ReponseModel.NewsResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ArticleAdapter.AdapterOnClickListener<Article> {
    private ArticleAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressDialog progressDoalog;
    private MainViewModel viewModel;
    private MainViewModelFactory factory;
    private List<Article> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initializeVariables();
        setUpProgressDialog();
        //fetchDataAndInsertIntoDb();
        setUpObservers();
        setUpRecyclerView();
    }

    private void initializeVariables() {
        factory = new MainViewModelFactory(this);
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        recyclerView = findViewById(R.id.customRecyclerView);
        adapter = new ArticleAdapter(this, this);
        progressDoalog = new ProgressDialog(MainActivity.this);
        articles = new ArrayList<>();

    }

    private void setUpObservers() {

        viewModel.articleLiveData.observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                if (articles != null) {
                    setUpData(articles);
                    progressDoalog.dismiss();

                }
            }
        });

        viewModel.isDeleted.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isDeleted) {
                if (isDeleted) {
                    showInfoAlertDialogNoIcon(MainActivity.this, "Alert", "Article has been deleted");

                }
            }
        });

        viewModel.getTasks("bitcoin", "39855b9e16bf4b21aabeaa39806004dd").observe(this, new Observer<NewsResponse>() {
            @Override
            public void onChanged(NewsResponse newsResponse) {
                if (newsResponse.getArticles().isEmpty()) {
                    progressDoalog.dismiss();
                    Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.insertDataToDb(newsResponse.getArticles());
                }
            }
        });
    }


    private void setUpData(List<Article> articles) {

        adapter.setDataList(articles);
    }

    private void setUpRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    private void setUpProgressDialog() {
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
    }

    @Override
    public void onItemSelected(Article article) {
        if (article != null) {
            handleDelete(article);
        }
    }


    private void handleDelete(final Article article) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert")
                .setMessage("Do you want to delete")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.deleteDataToDb(article);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}