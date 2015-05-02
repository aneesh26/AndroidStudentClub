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
 *          This module is used to retrieve the About Us information from the server
 *
 * @author : Aneesh Shastry  mailto:ashastry@asu.edu
 *           MS Computer Science, CIDSE, IAFSE, Arizona State University
 * @version : May 1, 2015
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by A on 4/7/2015.
 */
public class AboutUsActivity extends Activity {
    JSONObject jObj = null;
    JSONArray jArray = null;
    public TextView aboutUsContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_us);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor((getResources().getColor(R.color.maroon_light)));

        try {
            jObj = new JSONObject((String)(getIntent().getSerializableExtra("AboutUs")));
            jArray = jObj.getJSONArray("AboutUsData");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        aboutUsContent = (TextView)findViewById(R.id.aboutUsContent);

        try {
            JSONObject object = null;
            object = jArray.getJSONObject(0);
            String temp = (String)object.getString("title") + "\n\n" + (String)object.getString("content")+ "\n\nEmail: " + (String)object.getString("email") + "\nContact: " + (String)object.getString("contact");
            aboutUsContent.setText(temp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
}
