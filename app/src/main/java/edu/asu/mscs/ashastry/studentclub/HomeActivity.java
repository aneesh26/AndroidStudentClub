package edu.asu.mscs.ashastry.studentclub;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.app.ListActivity;
import java.util.ArrayList;


public class HomeActivity extends ListActivity {

    ArrayList<String> waypointList = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        waypointList.add("Events");
        waypointList.add("News");
        waypointList.add("facebook page");
        waypointList.add("F.A.Qs");
        waypointList.add("About Us");
        waypointList.add("Settings");

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, waypointList);
        setListAdapter(adapter);

        //adding a new commen line





















    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
     //   getMenuInflater().inflate(R.menu.menu_home, menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
     //   return true;
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
}
