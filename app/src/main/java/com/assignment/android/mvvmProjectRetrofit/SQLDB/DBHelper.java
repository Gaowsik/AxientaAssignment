package com.assignment.android.mvvmProjectRetrofit.SQLDB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.assignment.android.mvvmProjectRetrofit.ReponseModel.Article;
import com.assignment.android.mvvmProjectRetrofit.ReponseModel.Source;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "articles.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "articles";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_URL_TO_IMAGE = "urlToImage";
    private static final String COLUMN_PUBLISHED_AT = "publishedAt";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_SOURCE_ID = "sourceId";
    private static final String COLUMN_SOURCE_NAME = "sourceName";
    // Add more column names as needed

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_URL + " TEXT, " +
                COLUMN_URL_TO_IMAGE + " TEXT, " +
                COLUMN_PUBLISHED_AT + " TEXT, " +
                COLUMN_CONTENT + " TEXT, " +
                COLUMN_SOURCE_ID + " TEXT, " +
                COLUMN_SOURCE_NAME + " TEXT)";
        // Add more columns to the create table query if needed

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create the table again
        onCreate(db);
    }

    // Rest of the methods...

    public void insertArticle(Article article) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID,article.getId());
        values.put(COLUMN_TITLE, article.getTitle());
        values.put(COLUMN_DESCRIPTION, article.getDescription());
        values.put(COLUMN_AUTHOR, article.getAuthor());
        values.put(COLUMN_URL, article.getUrl());
        values.put(COLUMN_URL_TO_IMAGE, article.getUrlToImage());
        values.put(COLUMN_PUBLISHED_AT, article.getPublishedAt());
        values.put(COLUMN_CONTENT, article.getContent());
        values.put(COLUMN_SOURCE_ID, article.getSource().getId());
        values.put(COLUMN_SOURCE_NAME, article.getSource().getName());
        // Set values for additional columns if needed

        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    @SuppressLint("Range")
    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Article article = new Article();
                article.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                article.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR)));
                article.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                article.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                article.setUrl(cursor.getString(cursor.getColumnIndex(COLUMN_URL)));
                article.setUrlToImage(cursor.getString(cursor.getColumnIndex(COLUMN_URL_TO_IMAGE)));
                article.setPublishedAt(cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHED_AT)));
                article.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)));

                // Retrieve the source information
                Source source = new Source();
                source.setId(cursor.getString(cursor.getColumnIndex(COLUMN_SOURCE_ID)));
                source.setName(cursor.getString(cursor.getColumnIndex(COLUMN_SOURCE_NAME)));
                article.setSource(source);

                articles.add(article);
            } while (cursor.moveToNext());
        }

        cursor.close();


        return articles;
    }

//    public void deleteArticle(Article article) {
//        SQLiteDatabase db = getWritableDatabase();
//        String whereClause = COLUMN_ID + " = ?";
//        String[] whereArgs = {String.valueOf(article.getId())};
//        db.delete(TABLE_NAME, whereClause, whereArgs);
//        db.close();
//    }

    public boolean deleteArticle(Article article) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(article.getId())};
        int rowsAffected = db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
        return rowsAffected > 0;
    }
}
