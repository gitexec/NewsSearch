package com.example.wington.newssearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.wington.newssearch.Article;
import com.example.wington.newssearch.ArticleArrayAdapter;
import com.example.wington.newssearch.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    EditText etQuery;
    GridView gvView;
    Button btnSearch;

    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViews();

    }
    public  void setupViews(){
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvView = (GridView) findViewById(R.id.gvResults);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this, articles);
        gvView.setAdapter(adapter);

        //hook up on item click listener
        gvView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //create an intent to display the article
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);

                //get the article to dsiplay
                Article article = articles.get(position);
                //pass in that article into intent
                i.putExtra("url",article.getWebUrl());
                startActivity(i);;
                //
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSearchButton(View view) {
        String seachText = etQuery.getText().toString();
        AsyncHttpClient nyTimeClient = new AsyncHttpClient();
        //http://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=95532f2d53aa49d28ee041b6a715047f
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();
        params.put("api-key","95532f2d53aa49d28ee041b6a715047f");
        params.put("page",0);
        params.put("q", seachText);

        nyTimeClient.get(url,params,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResult = null;
                try {
                    articleJsonResult = response.getJSONObject("response").getJSONArray("docs");
                    Log.d("DEBUG", articleJsonResult.toString());
                    adapter.addAll(Article.fromJSONARRAY(articleJsonResult));
                    Log.d("DEBUG", articles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //Toast.makeText(this, "Search for " + seachText, Toast.LENGTH_LONG).show();
    }
}
