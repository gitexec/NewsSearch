package com.example.wington.newssearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by wington on 1/28/17.
 */

public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    public ArticleArrayAdapter(Context context, List<Article> articles){
        super(context,android.R.layout.simple_list_item_1, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get data item for current position
         Article article = getItem(position);
        //check existing view being reused
        //Since not using recycled view > inflate the layout
        if(convertView == null){
            LayoutInflater inflater =   LayoutInflater.from(getContext());
            convertView =   inflater.inflate(R.layout.item_article_result, parent, false);
        }

        //find image view
        ImageView image = (ImageView)convertView.findViewById(R.id.ivImage);
        //Clear content view
        image.setImageResource(0);

        TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(article.getHeadline());
        String thumbNail = article.getThumbNail();

        if(!TextUtils.isEmpty(thumbNail)){
            Picasso.with(getContext()).load(thumbNail).into(image);
        }
        return convertView;
    }
}
