package edu.asu.mscs.ashastry.studentclub;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by A on 4/7/2015.
 */
public class NewsActivity extends Activity {

    JSONObject jObj = null;
    JSONArray jArray = null;
    int pos = 0;
    Button prev;
    Button next;


    ArrayList<NewsItem> newsList = new ArrayList<NewsItem>();
    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        prev = (Button)findViewById(R.id.prevButton);
        next = (Button)findViewById(R.id.nextButton);
        if(pos == 0){
            prev.setEnabled(false);
            ((TextView)findViewById(R.id.pageNum)).setText((pos+1) + "/" + newsList.size()) ;
        }
        try {
            jObj = new JSONObject((String) (getIntent().getSerializableExtra("ListOfNews")));
            jArray = jObj.getJSONArray("NewsData");


            for (int n = 0; n < jArray.length(); n++) {
                JSONObject object = null;
                try {
                    object = jArray.getJSONObject(n);
                    String newsDateString = object.getString("date");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date newsDate = sdf.parse(newsDateString);
                    NewsItem nItem = new NewsItem(object.getString("name"),newsDate,object.getString("content"));
                    newsList.add(nItem);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                // do some stuff....
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        loadNews();
        ((TextView)findViewById(R.id.pageNum)).setText((pos+1) + "/" + newsList.size()) ;

    }


    private void loadNews(){
        ((TextView)findViewById(R.id.newsTitle)).setText(this.newsList.get(this.pos).getNewsTitle());
        ((TextView)findViewById(R.id.newsDate)).setText(this.newsList.get(this.pos).getNewsDate().toString());
        ((TextView)findViewById(R.id.newsBody)).setText(this.newsList.get(this.pos).getNewsBody());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void nextClick(View v){
        if(pos < newsList.size()){
            pos++;
            loadNews();
            prev.setEnabled(true);
        }
        if(pos == newsList.size()-1){
            next.setEnabled(false);
            prev.setEnabled(true);
        }
        ((TextView)findViewById(R.id.pageNum)).setText((pos+1) + "/" + newsList.size()) ;

    }

    public void prevClick(View v){
        if(pos > 0){
            pos--;
            loadNews();
            next.setEnabled(true);
        }
        if(pos == 0){
            prev.setEnabled(false);
            next.setEnabled(true);
        }
        ((TextView)findViewById(R.id.pageNum)).setText((pos+1) + "/" + newsList.size()) ;

    }

}
