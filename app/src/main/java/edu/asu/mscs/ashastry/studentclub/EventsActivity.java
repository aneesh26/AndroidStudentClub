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
 *          This module is used to setup the Events Activity
 *
 * @author : Aneesh Shastry  mailto:ashastry@asu.edu
 *           MS Computer Science, CIDSE, IAFSE, Arizona State University
 * @version : May 1, 2015
 */

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.NavUtils;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
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
import java.util.EventListener;


public class EventsActivity extends ListActivity {
    ArrayList<EventItem> eventList = new ArrayList<EventItem>();
    AccessoriesAdapter adapter;
    JSONObject jObj = null;
    JSONArray jArray = null;
    ListView listview;






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_events);
        getActionBar().setDisplayHomeAsUpEnabled(true);



        try {
            jObj = new JSONObject((String)(getIntent().getSerializableExtra("ListOfEvents")));
            jArray = jObj.getJSONArray("EventsData");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        for(int n = 0; n < jArray.length(); n++)
        {
            JSONObject object = null;
            try {
                object = jArray.getJSONObject(n);
                String eventDateString = object.getString("date");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date eventDate = sdf.parse(eventDateString);

                long MINS = 60*1000;

                Date endTime = new Date(eventDate.getTime() + object.getInt("duration") * MINS);

                String dateString = eventDate.toString().substring(0,10);


                EventItem e = new EventItem(object.getString("name"),(dateString + " @ " + object.getString("location")),(eventDate.toString().substring(11, 16) + " - " + endTime.toString().substring(11, 16)),eventDate,object.getInt("duration"));
                eventList.add(e);


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // do some stuff....
        }


      //  listview = (ListView) findViewById(R.id.list);
        adapter = new AccessoriesAdapter();
       // listview.setAdapter(adapter);


        //adapter.notifyDataSetChanged();
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
        public CheckBox star;
        public TextView titleContent;
        public TextView detailsContent;
        public TextView timeContent;
        public ImageButton addButton;
    }

    private class AccessoriesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return eventList.size();//CHEESES.length;
        }

        @Override
        public String getItem(int position) {
            return eventList.get(position).getEventTitle();
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
                convertView = getLayoutInflater().inflate(R.layout.event_item, parent, false);

                holder = new AccessoriesViewHolder();
              //  holder.star = (CheckBox) convertView.findViewById(R.id.btn_star);
             //   holder.star.setOnCheckedChangeListener(mStarCheckedChanceChangeListener);
                holder.titleContent = (TextView) convertView.findViewById(R.id.titleContent);
                holder.detailsContent = (TextView) convertView.findViewById(R.id.detailsContent);
                holder.timeContent = (TextView) convertView.findViewById(R.id.timeContent);
                holder.addButton =  (ImageButton)convertView.findViewById(R.id.btn_add);
                holder.addButton.setOnClickListener(
                        new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                              int position = (int)v.getTag();

                                Calendar calSTime = Calendar.getInstance();
                                Calendar calETime = Calendar.getInstance();


                                Date startDate = eventList.get(position).getEventDate();
                                int duration =  eventList.get(position).getDuration();
                                String title = eventList.get(position).getEventTitle();

                                calSTime.setTime(startDate);
                                calETime.setTime(startDate );

                                Calendar cal = Calendar.getInstance();
                                Intent intent = new Intent(Intent.ACTION_EDIT);
                                intent.setType("vnd.android.cursor.item/event");
                                intent.putExtra("beginTime", calSTime.getTimeInMillis());
                                intent.putExtra("allDay", false);
                                intent.putExtra("rrule", "FREQ=DAILY;COUNT=1");
                                intent.putExtra("endTime", calSTime.getTimeInMillis()+ duration *60*1000);
                                intent.putExtra("title", "[Student Club]: " + title);
                                intent.putExtra("description", "This is a sample description");
                                showMessage("Adding Event to Calendar");
                                startActivity(intent);
                            }
                        }


                );

                convertView.setTag(holder);
                holder.addButton.setTag(position);



            } else {
                holder = (AccessoriesViewHolder) convertView.getTag();
            }
            if(position % 2 == 1) {
                convertView.setBackgroundColor(getResources().getColor(R.color.maroon_light));
            }else{
                convertView.setBackgroundColor(getResources().getColor(R.color.maroon_dark));
            }
          //  holder.star.setChecked(mStarStates[position]);
            holder.titleContent.setText(eventList.get(position).getEventTitle());
            holder.titleContent.setTextColor(getResources().getColor(R.color.maroon_font));
            holder.detailsContent.setText(eventList.get(position).getEventLocation());
            holder.detailsContent.setTextColor(getResources().getColor(R.color.maroon_font));
            holder.timeContent.setText(eventList.get(position).getEventTime());
            holder.timeContent.setTextColor(getResources().getColor(R.color.maroon_font));

            return convertView;
        }
    }

    private void showMessage(String message) {
        Toast.makeText(EventsActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private OnClickListener mBuyButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int)v.getTag();
          //  showMessage("Clicked " + position);
            // TODO Cyril: Not implemented yet!

        }
    };

    private OnCheckedChangeListener mStarCheckedChanceChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // TODO Cyril: Not implemented yet!
        }
    };



}
