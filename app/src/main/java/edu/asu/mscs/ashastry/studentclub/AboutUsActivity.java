package edu.asu.mscs.ashastry.studentclub;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.NavUtils;
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
            String temp = (String)object.getString("title") + "\n\n" + (String)object.getString("content")+ "\n\nEmail Us: " + (String)object.getString("email");
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
