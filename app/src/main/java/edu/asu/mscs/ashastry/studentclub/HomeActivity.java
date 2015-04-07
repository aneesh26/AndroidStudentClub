package edu.asu.mscs.ashastry.studentclub;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.app.ListActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;


public class HomeActivity extends ListActivity {

    ArrayList<String> waypointList = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    String result;

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

        //adapter.notifyDataSetChanged();
        setListAdapter(adapter);

    }


    protected void onListItemClick(ListView l, View v1, int position, long id) {
        String item = (String) getListAdapter().getItem(position);

        if(item.equals("Events")){
            Intent myIntent = new Intent(HomeActivity.this, EventsActivity.class);
            HomeActivity.this.startActivity(myIntent);
            Toast.makeText(HomeActivity.this, "Events Clicked", Toast.LENGTH_SHORT).show();
        }else if(item.equals("News")){
            Intent myIntent = new Intent(HomeActivity.this, NewsActivity.class);
            HomeActivity.this.startActivity(myIntent);
            Toast.makeText(HomeActivity.this, "News Clicked", Toast.LENGTH_SHORT).show();
        }else if(item.equals("facebook page")){
            Toast.makeText(HomeActivity.this, "FB Clicked", Toast.LENGTH_SHORT).show();
           startActivity(getOpenFacebookIntent(this.getApplicationContext()));
        }else if(item.equals("F.A.Qs")){
            Toast.makeText(HomeActivity.this, "FAQ Clicked", Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(HomeActivity.this, FAQsActivity.class);
            HomeActivity.this.startActivity(myIntent);

        }else if(item.equals("About Us")){
         //   getAboutUs();
            Toast.makeText(HomeActivity.this, "About Us Clicked", Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(HomeActivity.this, AboutUsActivity.class);
            HomeActivity.this.startActivity(myIntent);

          //  Toast.makeText(HomeActivity.this, this.result, Toast.LENGTH_LONG).show();
        }else if(item.equals("Settings")){
            Toast.makeText(HomeActivity.this, "Settings Clicked", Toast.LENGTH_SHORT).show();
        }
    }


    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/152880021441864"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/isa.asu"));
        }
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


    public void getAboutUs(){
        try {
            URL URLOut = new URL(this.getString(R.string.url_string));
            MyTaskParams mp = new MyTaskParams(URLOut, "getAboutUs");
            AsyncCalc ac = (AsyncCalc) new AsyncCalc().execute(mp);
        }

        catch (MalformedURLException m1){
            android.util.Log.d("Error in URL","");
        }

        catch (Exception ex) {
            android.util.Log.d(this.getClass().getSimpleName(), "Error connecting to the Server");
        }
    }


    private static class MyTaskParams {
        URL newURL;
        String operatorV;


        MyTaskParams(URL url, String op) {
            this.newURL = url;
            this.operatorV = op;

        }

        public URL getNewURL() {
            return newURL;
        }

        public void setNewURL(URL newURL) {
            this.newURL = newURL;
        }

        public String getOperatorV() {
            return operatorV;
        }

        public void setOperatorV(String operatorV) {
            this.operatorV = operatorV;
        }
    }
    private class AsyncCalc extends AsyncTask<MyTaskParams, Integer, String> {

        double val = 0.0;
        String returnValue = null;
        int errorFlag = 0;
        protected String doInBackground(MyTaskParams... mtp) {
            try{
                ClubStub calc = new ClubStub(mtp[0].getNewURL(),errorFlag);
                if(errorFlag == 1){
                   android.util.Log.d(this.getClass().getSimpleName(),"Error with ClubStub class");
                   // ((TextView)HomeActivity.this.findViewById(R.id.messageView)).setText("Cannot connect to the server");
                }
                String oper = mtp[0].getOperatorV();
                //double leftOp = exp1;
                //double rightOp = exp2;
                if("getAboutUs".equals(oper)){
                    returnValue = calc.getAboutUs();
                }
              /*  else if("Subtract".equals(oper)){
                    val = calc.sub(leftOp, rightOp);
                }
                else if("Multiply".equals(oper)){
                    val = calc.mul(leftOp, rightOp);
                }
                else if("Divide".equals(oper)){
                    val = calc.div(leftOp, rightOp);
                }*/
            }


            catch(Exception ex){
                ex.printStackTrace();
           //     ((TextView)findViewById(R.id.messageView)).setText("Error Creating Async task");
             //   ((TextView)findViewById(R.id.displayNum)).setText("");
            }
            return returnValue;
        }

        protected void onPostExecute(String res){
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);
            android.util.Log.d(this.getClass().getSimpleName(),res);
            HomeActivity.this.result = res;
            Toast.makeText(HomeActivity.this, res, Toast.LENGTH_LONG).show();
            //((TextView)findViewById(R.id.displayNum)).setText(nf.format(res));
            //exp1 = 0;
           // exp2 = 0;
           // decExp = 0;
        }
    }

}
