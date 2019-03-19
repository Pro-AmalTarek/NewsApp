package com.amaltarek.newsapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.amaltarek.newsapp.Model.News;
import com.amaltarek.newsapp.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
// Just nitpicking here, but this is an unused import and should be removed for optimal formatting. I usually run the Code Inspector to get such (and many other tips and improvements suggested by Android).
// By click on Analyze -- inspect Code


public class NewsAdapter extends ArrayAdapter<News> {

    // Context of the Activity
    private Context mContext;

    public NewsAdapter(Context context, ArrayList<News> newsLst) {
        super(context,0, newsLst);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        News currentNews = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        // Get the UI Art News and fill Content
        ImageView newsArt = convertView.findViewById(R.id.news_thumbnail);
        String thumbnail = currentNews.getNewsArt();
        if(TextUtils.isEmpty(thumbnail)){
            newsArt.setImageResource(R.drawable.news);
        }else{
            Glide.with(mContext).load(thumbnail).into(newsArt);
        }

        // Check if Their author Name or not
        TextView authorName = convertView.findViewById(R.id.author_name);
        String author = currentNews.getAuthorName();
        if(TextUtils.isEmpty(author)){
           authorName.setText(mContext.getString(R.string.by_Anonymous));
        }else{
            authorName.setText(author);
        }

        TextView dateText = convertView.findViewById(R.id.news_date);
        dateText.setText(currentNews.getPublicationDate());

        TextView sectionName = convertView.findViewById(R.id.section_name);
        sectionName.setText(currentNews.getSectionName());

        TextView newsTitle = convertView.findViewById(R.id.news_title);
        newsTitle.setText(currentNews.getNewsTitle());

        return convertView;
    }

}
