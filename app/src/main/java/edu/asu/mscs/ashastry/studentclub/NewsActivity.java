package edu.asu.mscs.ashastry.studentclub;

/**
 * Copyright 2015 Aneesh Shastry
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Purpose: This is a Student Club App to enable Clubs to share information with its members.
 *          This module is used to setup the News Activity
 *
 * @author : Aneesh Shastry  mailto:ashastry@asu.edu
 *           MS Computer Science, CIDSE, IAFSE, Arizona State University
 * @version : May 1, 2015
 */


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

        View view = this.getWindow().getDecorView();
        view.setBackgroundColor((getResources().getColor(R.color.maroon_light)));
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
