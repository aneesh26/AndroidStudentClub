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
 *          This module is used to setup the FAQs Activity
 *
 * @author : Aneesh Shastry  mailto:ashastry@asu.edu
 *           MS Computer Science, CIDSE, IAFSE, Arizona State University
 * @version : May 1, 2015
 */

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class FAQsActivity extends ListActivity {

    ArrayList<FAQItem> faqList = new ArrayList<FAQItem>();
    AccessoriesAdapter adapter;
    JSONObject jObj = null;
    JSONArray jArray = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_faqs);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        View view = this.getWindow().getDecorView();
        view.setBackgroundColor((getResources().getColor(R.color.maroon_light)));

        try {
            jObj = new JSONObject((String)(getIntent().getSerializableExtra("ListOfFAQ")));
            jArray = jObj.getJSONArray("FAQsData");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int n = 0; n < jArray.length(); n++)
        {
            JSONObject object = null;
            try {
                object = jArray.getJSONObject(n);
                String question = object.getString("question");
                String answer = object.getString("answer");

                FAQItem f = new FAQItem(object.getString("question"),object.getString("answer"),object.getInt("id"));
                faqList.add(f);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            // do some stuff....
        }

        adapter = new AccessoriesAdapter();
        setListAdapter(this.adapter);

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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
       // showMessage("Test");//getString(R.string.you_want_info_about_format, CHEESES[position]));
    }

    private static class AccessoriesViewHolder {

        public TextView questionTV;
        public TextView answerTV;

    }

    private class AccessoriesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return faqList.size();//CHEESES.length;
        }

        @Override
        public String getItem(int position) {
            return faqList.get(position).getQuestion();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            AccessoriesViewHolder holder = null;

            if (convertView == null) {
                //convertView = getLayoutInflater().inflate(R.layout.activity_events, parent, false);
                convertView = getLayoutInflater().inflate(R.layout.faq_item, parent, false);

                holder = new AccessoriesViewHolder();
                //  holder.star = (CheckBox) convertView.findViewById(R.id.btn_star);
                //   holder.star.setOnCheckedChangeListener(mStarCheckedChanceChangeListener);
                holder.questionTV = (TextView) convertView.findViewById(R.id.faqQuestion);
                holder.answerTV = (TextView) convertView.findViewById(R.id.faqAnswer);
                convertView.setTag(holder);

            } else {
                holder = (AccessoriesViewHolder) convertView.getTag();
            }
            if(position % 2 == 1) {
                convertView.setBackgroundColor(getResources().getColor(R.color.maroon_light));
            }else{
                convertView.setBackgroundColor(getResources().getColor(R.color.maroon_dark));
            }
           // holder.homeItem.setTextColor(getResources().getColor(R.color.maroon_font));
            //  holder.star.setChecked(mStarStates[position]);
            holder.questionTV.setText(position+1 + ". " + faqList.get(position).getQuestion());
            holder.questionTV.setTextColor(getResources().getColor(R.color.maroon_font));
            holder.answerTV.setText("-- " + faqList.get(position).getAnswer());
            holder.answerTV.setTextColor(getResources().getColor(R.color.maroon_font));
            return convertView;
        }
    }

    private void showMessage(String message) {
       Toast.makeText(FAQsActivity.this, message, Toast.LENGTH_SHORT).show();
    }


}
