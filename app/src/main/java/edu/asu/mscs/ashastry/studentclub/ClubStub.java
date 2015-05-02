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
 *          This module is used to communicate with the server
 *
 * @author : Aneesh Shastry  mailto:ashastry@asu.edu
 *           MS Computer Science, CIDSE, IAFSE, Arizona State University
 * @version : May 1, 2015
 */


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;


public class ClubStub {
    int errorF = 0;
    private JsonRpcRequestViaHttp server;
    private static int id=0;
    ClubStub(URL url,int errorFlag){
        server = new JsonRpcRequestViaHttp(url);
        this.errorF = errorFlag;
    }

    public String getAboutUs(){
        String res = null;

        try{
            JSONArray ja = new JSONArray();
          //  ja.put(0,left).put(1,right);
            String callStr = this.packageCalcCall("getAboutUs",ja);
            android.util.Log.d("Client --> Server",callStr);
            System.out.println("Before server call");
            String retStr = server.call(callStr);
            System.out.println("After server call");
            android.util.Log.d("Server --> Client",retStr);
            JSONObject result = new JSONObject(retStr);
            res = result.optString("result");

        }
        catch(ConnectException ex1){
            errorF = 1;
        }
        catch(Exception ex){

            android.util.Log.d(this.getClass().getSimpleName(),"Error with JSON add method");
            ex.printStackTrace();
        }
        return res;
    }

    public String getEvents(){
        String res = null;

        try{
            JSONArray ja = new JSONArray();
            //  ja.put(0,left).put(1,right);
            String callStr = this.packageCalcCall("getEvents",ja);
            android.util.Log.d("Client --> Server",callStr);
            System.out.println("Before server call");
            String retStr = server.call(callStr);
            System.out.println("After server call");
            android.util.Log.d("Server --> Client",retStr);
            JSONObject result = new JSONObject(retStr);
            res = result.optString("result");

        }
        catch(ConnectException ex1){
            errorF = 1;
        }
        catch(Exception ex){

            android.util.Log.d(this.getClass().getSimpleName(),"Error with JSON getEvents method");
            ex.printStackTrace();
        }
        return res;
    }

    public String getNews(){
        String res = null;

        try{
            JSONArray ja = new JSONArray();
            //  ja.put(0,left).put(1,right);
            String callStr = this.packageCalcCall("getNews",ja);
            android.util.Log.d("Client --> Server",callStr);
            System.out.println("Before server call");
            String retStr = server.call(callStr);
            System.out.println("After server call");
            android.util.Log.d("Server --> Client",retStr);
            JSONObject result = new JSONObject(retStr);
            res = result.optString("result");

        }
        catch(ConnectException ex1){
            errorF = 1;
        }
        catch(Exception ex){

            android.util.Log.d(this.getClass().getSimpleName(),"Error with JSON getNews method");
            ex.printStackTrace();
        }
        return res;
    }

    public String getFAQ(){
        String res = null;

        try{
            JSONArray ja = new JSONArray();
            //  ja.put(0,left).put(1,right);
            String callStr = this.packageCalcCall("getFAQ",ja);
            android.util.Log.d("Client --> Server",callStr);
            System.out.println("Before server call");
            String retStr = server.call(callStr);
            System.out.println("After server call");
            android.util.Log.d("Server --> Client",retStr);
            JSONObject result = new JSONObject(retStr);
            res = result.optString("result");

        }
        catch(ConnectException ex1){
            errorF = 1;
        }
        catch(Exception ex){

            android.util.Log.d(this.getClass().getSimpleName(),"Error with JSON getNews method");
            ex.printStackTrace();
        }
        return res;
    }

    private String packageCalcCall(String oper, JSONArray args){
        String ret = "";
        try{
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("jsonrpc","2.0");
            jsonObj.put("method",oper);
            jsonObj.put("id", ++id);
            String almost = jsonObj.toString();
            String toInsert = null;
            if(args == null){
                toInsert = ",\"params\":[]";
            } else{
                toInsert = ",\"params\":" + args.toString();
            }
            String begin = almost.substring(0,almost.length()-1);
            String end = almost.substring(almost.length()-1);
            ret = begin + toInsert + end;
        }
        catch(Exception ex){
            android.util.Log.d(this.getClass().getSimpleName(),"Error with packageCalcCall method");

        }
        return ret;
    }


    public static boolean isHostReachable(String serverAddress, int serverTCPport, int timeoutMS){
        boolean connected = false;
        Socket socket;
        try {
            socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(serverAddress, serverTCPport);
            socket.connect(socketAddress, timeoutMS);
            if (socket.isConnected()) {
                connected = true;
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket = null;
        }
        return connected;
    }
 /*
    public double add(double left, double right) {
        double res = 0.0;
        try{
            JSONArray ja = new JSONArray();
            ja.put(0,left).put(1,right);
            String callStr = this.packageCalcCall("add",ja);
            android.util.Log.d("Client --> Server",callStr);
            String retStr = server.call(callStr);
            android.util.Log.d("Server --> Client",retStr);
            JSONObject result = new JSONObject(retStr);
            res = result.optDouble("result");

        }
        catch(ConnectException ex1){
            errorF = 1;
        }
        catch(Exception ex){

            android.util.Log.d(this.getClass().getSimpleName(),"Error with JSON add method");
        }
        return res;
    }
    public double sub(double left, double right){
        double res = 0.0;
        try{
            JSONArray ja = new JSONArray();
            ja.put(0,left).put(1,right);
            String callStr = this.packageCalcCall("subtract",ja);
            android.util.Log.d("Client --> Server",callStr);
            String retStr = server.call(callStr);
            android.util.Log.d("Server --> Client",retStr);
            JSONObject result = new JSONObject(retStr);
            res = result.optDouble("result");

        }
        catch(ConnectException ex1){
            errorF = 1;
        }
        catch(Exception ex){
            android.util.Log.e("Sub","Error in subtract call function");

        }

        return res;

    }
    public double mul(double left, double right){
        double res = 0.0;
        try{
            JSONArray ja = new JSONArray();
            ja.put(0,left).put(1,right);

            String callStr = this.packageCalcCall("multiply",ja);
            android.util.Log.d("Client --> Server",callStr);
            String retStr = server.call(callStr);
            android.util.Log.d("Server --> Client",retStr);
            JSONObject result = new JSONObject(retStr);
            res = result.optDouble("result");


        }
        catch(ConnectException ex1){
            errorF = 1;
        }
        catch(Exception ex){
            android.util.Log.e("Sub","Error in multiply call function");

        }
        return res;
    }
    public double div(double left, double right){
        double res = 0.0;
        try{
            JSONArray ja = new JSONArray();
            ja.put(0,left).put(1,right);
            String callStr = this.packageCalcCall("divide",ja);
            android.util.Log.d("Client --> Server",callStr);
            String retStr = server.call(callStr);
            android.util.Log.d("Server --> Client",retStr);
            JSONObject result = new JSONObject(retStr);
            res = result.optDouble("result");

        }
        catch(ConnectException ex1){
            errorF = 1;
        }
        catch(Exception ex){
            android.util.Log.e("Sub", "Error in divide call function");

        }
        return res;
    }
*/

}
