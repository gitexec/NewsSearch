package com.example.wington.newssearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wington on 1/28/17.
 */

public class Article implements Serializable {
    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    String webUrl;
    String headline;
    String thumbNail;

    public Article(JSONObject articleResult){
        try {
            this.webUrl = articleResult.getString("web_url");
            this.headline = articleResult.getJSONObject("headline").getString("main")   ;
            JSONArray multimedia = articleResult.getJSONArray("multimedia");
            if(multimedia.length() > 0){
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.thumbNail = "http:www.nytimes.com/" + multimediaJson.getString("url");
            }else{
                this.thumbNail = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Article> fromJSONARRAY(JSONArray jsonArray){
        ArrayList<Article> articles = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++){
            try {
                articles.add(new Article(jsonArray.getJSONObject(i))) ;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return articles;
    }

}


