package com.assignment.android.mvvmProjectRetrofit.ArticleAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.android.mvvmProjectRetrofit.Constants;
import com.assignment.android.mvvmProjectRetrofit.R;
import com.assignment.android.mvvmProjectRetrofit.ReponseModel.Article;
import com.assignment.android.mvvmProjectRetrofit.Utills;
import com.assignment.android.mvvmProjectRetrofit.ui.ArticleDetailsActivity;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Article> dataList;
    private Context context;
    private AdapterOnClickListener<Article> adapterItemTypeOnClickListener;

    public interface AdapterOnClickListener<T> {
        void onItemSelected(T item);}

    public ArticleAdapter(Context context,AdapterOnClickListener<Article> adapterItemTypeOnClickListener) {
        this.context = context;
        dataList = new ArrayList<>();
        this.adapterItemTypeOnClickListener = adapterItemTypeOnClickListener;

    }

    public void setDataList(List<Article> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        ImageView imageView;
        TextView textAuthor;
        TextView textSource;
        TextView textTitle;
        LinearLayout linearLayout;

        ArticleViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            imageView = itemView.findViewById(R.id.imageView);
            textAuthor = itemView.findViewById(R.id.textAuthor);
            textSource = itemView.findViewById(R.id.textSource);
            textTitle = itemView.findViewById(R.id.textTitle);
            linearLayout  = itemView.findViewById(R.id.linearMain);
        }
    }

    @Override
    public ArticleAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.article_item, parent, false);
        return new ArticleAdapter.ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {

        final Article currentItem = (Article) dataList.get(position);
        holder.textAuthor.setText(dataList.get(position).getAuthor());
        holder.textTitle.setText(dataList.get(position).getTitle());
        holder.textSource.setText(dataList.get(position).getSource().getName());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getUrlToImage()).placeholder((R.drawable.ic_launcher_background)).error(R.drawable.ic_launcher_background).into(holder.imageView);

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (adapterItemTypeOnClickListener != null) {
                        adapterItemTypeOnClickListener.onItemSelected(currentItem);
                        return true;
                }
                return false;
            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(context, ArticleDetailsActivity.class);
                i.putExtra(Constants.BUNDLE_ARTICLE, Utills.getObjectAsString(currentItem));
                context.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

