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
 *          This module is used to setup the Home Activity
 *          This is the main module where the aynchronous interaction with the server takes place.
 *          The data retrieved from the server is shared across with the different activities
 *
 * @author : Aneesh Shastry  mailto:ashastry@asu.edu
 *           MS Computer Science, CIDSE, IAFSE, Arizona State University
 * @version : May 1, 2015
 */


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.app.ListActivity;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.json.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class HomeActivity extends ListActivity {

    ArrayList<String> menuList = new ArrayList<String>();
    //ArrayAdapter<String> adapter;
    AccessoriesAdapter adapter;
    String result;
    JSONObject jObject = null;
    String method = null;
    String sharedURL = "http://192.168.0.18:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("serverURL", this.sharedURL).commit();

        View view = this.getWindow().getDecorView();
        view.setBackgroundColor((getResources().getColor(R.color.maroon_light)));
        menuList.add("Events");
        menuList.add("News");
        menuList.add("facebook");
        menuList.add("F.A.Qs");
        menuList.add("About Us");
        menuList.add("Settings");

     //   adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menuList);
        adapter = new AccessoriesAdapter();
        //adapter.notifyDataSetChanged();
        setListAdapter(adapter);



    }


    protected void onListItemClick(ListView l, View v1, int position, long id) {
        String item = (String) getListAdapter().getItem(position);

        if(item.equals("Events")){
            this.getEvents();

            Toast.makeText(HomeActivity.this, "Opening Events", Toast.LENGTH_SHORT).show();
        }else if(item.equals("News")){
            this.getNews();

            Toast.makeText(HomeActivity.this, "Opening News", Toast.LENGTH_SHORT).show();
        }else if(item.equals("facebook page")){
            Toast.makeText(HomeActivity.this, "Opening facebook", Toast.LENGTH_SHORT).show();
           startActivity(getOpenFacebookIntent(this.getApplicationContext()));
        }else if(item.equals("F.A.Qs")){
            this.getFAQ();

            Toast.makeText(HomeActivity.this, "Opening FAQs", Toast.LENGTH_SHORT).show();
        }else if(item.equals("About Us")){
            this.getAboutUs();

            Toast.makeText(HomeActivity.this, "Opening AboutUs", Toast.LENGTH_SHORT).show();

          //  Toast.makeText(HomeActivity.this, this.result, Toast.LENGTH_LONG).show();
        }else if(item.equals("Settings")){
            Intent myIntent = new Intent(HomeActivity.this, SettingsActivity.class);
            HomeActivity.this.startActivity(myIntent);
            Toast.makeText(HomeActivity.this, "Opening Settings", Toast.LENGTH_SHORT).show();
        }
    }


    public static Intent getOpenFacebookIntent(Context context) {
        String fbURL = "https://www.facebook.com/isa.asu";
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            Uri uri = Uri.parse("fb://facewebmodal/f?href=" + fbURL);
            return new Intent(Intent.ACTION_VIEW,uri);
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/isa.asu"));
        }
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
        Intent myIntent = new Intent(HomeActivity.this, SettingsActivity.class);
        HomeActivity.this.startActivity(myIntent);
        return super.onOptionsItemSelected(item);
    }


    public void getAboutUs(){
        try {
            method = null;

            sharedURL = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("serverURL", "");

            //URL URLOut = new URL(this.getString(R.string.url_string));
            URL URLOut = new URL(sharedURL);
            method = "getAboutUs";
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

    public void getEvents(){
        try {
            method = "getEvents";
            sharedURL = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("serverURL", "");

            //URL URLOut = new URL(this.getString(R.string.url_string));
            URL URLOut = new URL(sharedURL);
            MyTaskParams mp = new MyTaskParams(URLOut, "getEvents");
            AsyncCalc ac = (AsyncCalc) new AsyncCalc().execute(mp);
          //  jObject = new JSONObject(result);
            JSONParser parser = new JSONParser();

            jObject = new JSONObject((Map)parser.parse(result));
        }

        catch (MalformedURLException m1){
            android.util.Log.d("Error in URL","");
        }

        catch (Exception ex) {
            android.util.Log.d(this.getClass().getSimpleName(), "Error connecting to the Server");
        }
    }

    public void getNews(){
        try {
            method = "getNews";
            sharedURL = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("serverURL", "");

            //URL URLOut = new URL(this.getString(R.string.url_string));
            URL URLOut = new URL(sharedURL); MyTaskParams mp = new MyTaskParams(URLOut, "getNews");
            AsyncCalc ac = (AsyncCalc) new AsyncCalc().execute(mp);
            JSONParser parser = new JSONParser();

            jObject = new JSONObject((Map)parser.parse(result));

            //jObject = new JSONObject(result);
        }

        catch (MalformedURLException m1){
            android.util.Log.d("Error in URL","");
        }

        catch (Exception ex) {
            android.util.Log.d(this.getClass().getSimpleName(), "Error connecting to the Server");
        }
    }

    public void getFAQ(){
        try {
            method = "getFAQ";
            sharedURL = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("serverURL", "");

            //URL URLOut = new URL(this.getString(R.string.url_string));
            URL URLOut = new URL(sharedURL); MyTaskParams mp = new MyTaskParams(URLOut, "getFAQ");
            AsyncCalc ac = (AsyncCalc) new AsyncCalc().execute(mp);

            JSONParser parser = new JSONParser();

            jObject = new JSONObject((Map)parser.parse(result));


            //jObject = new JSONObject(result);
        }

        catch (MalformedURLException m1){
            android.util.Log.d("Error in URL","");
        }

        catch (Exception ex) {
            android.util.Log.d(this.getClass().getSimpleName(), "Error connecting to the Server");
        }
    }

    public void callEvents(String s){
        try {
            JSONParser parser = new JSONParser();
            //Map jsonData = parser.parse(s);
            jObject = new JSONObject((Map)parser.parse(s));
            Intent myIntent = new Intent(HomeActivity.this, EventsActivity.class);
            myIntent.putExtra("ListOfEvents",jObject.toString());
            HomeActivity.this.startActivity(myIntent);
        } //catch (JSONException e) {
          //  e.printStackTrace();
    //    }
            catch (ParseException e) {
            e.printStackTrace();
        }
        android.util.Log.d(this.getClass().getSimpleName(),s);
    }

    public void callNews(String s){
        try {
            //jObject = new JSONObject(s);
            JSONParser parser = new JSONParser();

            jObject = new JSONObject((Map)parser.parse(s));
            Intent myIntent = new Intent(HomeActivity.this, NewsActivity.class);
            myIntent.putExtra("ListOfNews",jObject.toString());
            HomeActivity.this.startActivity(myIntent);
        } //catch (JSONException e) {
          //  e.printStackTrace();
    //    }
        catch (ParseException e) {
            e.printStackTrace();
        }
        android.util.Log.d(this.getClass().getSimpleName(),s);
    }

    public void callFAQ(String s){
        try {
            JSONParser parser = new JSONParser();

            jObject = new JSONObject((Map)parser.parse(s));

            //jObject = new JSONObject(s);
            Intent myIntent = new Intent(HomeActivity.this, FAQsActivity.class);
            myIntent.putExtra("ListOfFAQ",jObject.toString());
            HomeActivity.this.startActivity(myIntent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        android.util.Log.d(this.getClass().getSimpleName(),s);
    }

    public void callAboutUs(String s){
        try {
            JSONParser parser = new JSONParser();

            jObject = new JSONObject((Map)parser.parse(s));

         //   jObject = new JSONObject(s);


            Intent myIntent = new Intent(HomeActivity.this, AboutUsActivity.class);
            myIntent.putExtra("AboutUs",jObject.toString());
            HomeActivity.this.startActivity(myIntent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        android.util.Log.d(this.getClass().getSimpleName(),s);
    }


    public void callOffline(){
        Toast.makeText(HomeActivity.this, "Cannot connect to server, using local data", Toast.LENGTH_LONG).show();
        if("getAboutUs".equals(method)){
            String s = callOfflineMethodDetails("aboutus");
            callAboutUs(s);
        }else if("getEvents".equals(method)){
            String s = callOfflineMethodDetails("events");
            callEvents(s);
        }else if("getNews".equals(method)){
            String s = callOfflineMethodDetails("news");
            callNews(s);
        }else if("getFAQ".equals(method)){
            String s = callOfflineMethodDetails("faqs");
            callFAQ(s);
        }

    }

    public String callOfflineMethodDetails(String file){
        String fileName = file;
        String filePath = this.getFilesDir().getPath()+"/";
        CreateFile cf = new CreateFile(HomeActivity.this, fileName);
        try {
            cf.doCreateFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        Object obj = null;
        File relative = new File(filePath + fileName + ".json");
        try {
            obj = parser.parse(new FileReader(relative));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        String s =  jsonObject.toString();

        return s;



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
        String operator = null;
        protected String doInBackground(MyTaskParams... mtp) {
            try{
                sharedURL = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("serverURL", "");

                //URL URLOut = new URL(this.getString(R.string.url_string));
               // URL URLOut = new URL(sharedURL); MyTaskParams mp = new MyTaskParams(URLOut, "getFAQ");
                String host = sharedURL; //(HomeActivity.this.getString(R.string.url_string));
                String url = host.split(":")[1];
                int port = Integer.parseInt(host.split(":")[2]);
               // InetAddress.getByName(host).isReachable(2000);

                Socket socket = null;
                boolean reachable = false;
                try {
                    Socket s1 = new Socket();
                    s1.setSoTimeout(200);
                    s1.connect(new InetSocketAddress(url.substring(url.lastIndexOf("/") + 1, url.length()), port), 2000);
                    String remote = s1.getRemoteSocketAddress().toString();
                    String local = s1.getLocalAddress().toString();
                    if((remote.substring(0,9)).equals(local.substring(0, 9))){
                        reachable = true; //to make sure both are in same subnet
                    }



                }
                catch(SocketTimeoutException ste){
                    android.util.Log.d(this.getClass().getSimpleName(), "Cannot reach server");
                }
                finally {
                    if (socket != null) try { socket.close(); }
                    catch(SocketTimeoutException ste){
                        android.util.Log.d(this.getClass().getSimpleName(),"Cannot reach server");
                    }
                }
                if(reachable){
                    android.util.Log.d(this.getClass().getSimpleName(),"Can reach server");

                    ClubStub calc = new ClubStub(mtp[0].getNewURL(),errorFlag);
                    if(errorFlag == 1){
                        android.util.Log.d(this.getClass().getSimpleName(),"Error with ClubStub class");
                        // ((TextView)HomeActivity.this.findViewById(R.id.messageView)).setText("Cannot connect to the server");
                    }
                    String oper = mtp[0].getOperatorV();
                    operator = oper;


                    //double leftOp = exp1;
                    //double rightOp = exp2;
                    if("getAboutUs".equals(oper)){
                        returnValue = calc.getAboutUs();
                    }else if("getEvents".equals(oper)){
                        returnValue = calc.getEvents();
                    }else if("getNews".equals(oper)){
                        returnValue = calc.getNews();
                    }else if("getFAQ".equals(oper)){
                        returnValue = calc.getFAQ();
                    }
                }else{
                    android.util.Log.d(this.getClass().getSimpleName(),"Cannot reach server");
                    operator = "offline";
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

            if("offline".equals(operator)){
                HomeActivity.this.callOffline();
            }
            else {
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
               // android.util.Log.d(this.getClass().getSimpleName(), res);
                HomeActivity.this.result = res;
                if ("getAboutUs".equals(operator)) {
                    HomeActivity.this.callAboutUs(res);
                } else if ("getEvents".equals(operator)) {
                    HomeActivity.this.callEvents(res);
                } else if ("getNews".equals(operator)) {
                    HomeActivity.this.callNews(res);
                } else if ("getFAQ".equals(operator)) {
                    HomeActivity.this.callFAQ(res);
                }  else{
                    HomeActivity.this.callOffline();
                }

            }
         //   Toast.makeText(HomeActivity.this, res, Toast.LENGTH_LONG).show();

            //((TextView)findViewById(R.id.displayNum)).setText(nf.format(res));
            //exp1 = 0;
           // exp2 = 0;
           // decExp = 0;
        }
    }



    private static class AccessoriesViewHolder {

        public TextView homeItem;
        public ImageView homeImage;

    }

    private class AccessoriesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return menuList.size();//CHEESES.length;
        }

        @Override
        public String getItem(int position) {
            return menuList.get(position);
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
                convertView = getLayoutInflater().inflate(R.layout.home_item, parent, false);

                holder = new AccessoriesViewHolder();
                //  holder.star = (CheckBox) convertView.findViewById(R.id.btn_star);
                //   holder.star.setOnCheckedChangeListener(mStarCheckedChanceChangeListener);
                holder.homeItem = (TextView) convertView.findViewById(R.id.homeItem);
                holder.homeImage = (ImageView) convertView.findViewById(R.id.homeImage);

                convertView.setTag(holder);
                if(position % 2 == 1) {
                    convertView.setBackgroundColor(getResources().getColor(R.color.maroon_light));
                }else{
                    convertView.setBackgroundColor(getResources().getColor(R.color.maroon_dark));
                }
                holder.homeItem.setTextColor(getResources().getColor(R.color.maroon_font));
              //  holder.addButton.setTag(position);

            } else {
                holder = (AccessoriesViewHolder) convertView.getTag();
            }

            //  holder.star.setChecked(mStarStates[position]);
            holder.homeItem.setText(menuList.get(position));
           if(position == 0){
                holder.homeImage.setImageResource(R.drawable.events);
           }else if(position == 1){
               holder.homeImage.setImageResource(R.drawable.news);
           }else if(position == 2){
               holder.homeImage.setImageResource(R.drawable.facebook);
           }else if(position == 3){
               holder.homeImage.setImageResource(R.drawable.faqs);
           }else if(position == 4){
               holder.homeImage.setImageResource(R.drawable.aboutus);
           }else if(position == 5){
               holder.homeImage.setImageResource(R.drawable.settings);
           }

            return convertView;
        }
    }


    private View.OnClickListener mBuyButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int)v.getTag();
           // showMessage("Clicked " + position);
            // TODO Cyril: Not implemented yet!

        }
    };

    private CompoundButton.OnCheckedChangeListener mStarCheckedChanceChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // TODO Cyril: Not implemented yet!
        }
    };


}
