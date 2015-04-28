package edu.asu.mscs.ashastry.studentclub;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by A on 4/7/2015.
 */
public class EventsActivity extends ListActivity {
    ArrayList<String> eventList = new ArrayList<String>();
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jArray = jObj.getJSONArray("EventsData");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int n = 0; n < jArray.length(); n++)
        {
            JSONObject object = null;
            try {
                object = jArray.getJSONObject(n);
                eventList.add(object.getString("name"));
            } catch (JSONException e) {
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
        showMessage("Test");//getString(R.string.you_want_info_about_format, CHEESES[position]));
    }

    private static class AccessoriesViewHolder {
        public CheckBox star;
        public TextView titleContent;
        public TextView detailsContent;
        public Button addButton;
    }

    private class AccessoriesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return eventList.size();//CHEESES.length;
        }

        @Override
        public String getItem(int position) {
            return eventList.get(position);
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
                holder.addButton =  (Button)convertView.findViewById(R.id.btn_add);
                holder.addButton.setOnClickListener(
                        new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                              int position = (int)v.getTag();
                                showMessage("Clicked " + position);
                                // TODO Cyril: Not implemented yet!

                            }
                        }


                );

                convertView.setTag(holder);
                holder.addButton.setTag(position);

            } else {
                holder = (AccessoriesViewHolder) convertView.getTag();
            }

          //  holder.star.setChecked(mStarStates[position]);
            holder.titleContent.setText(eventList.get(position));

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
            showMessage("Clicked " + position);
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
