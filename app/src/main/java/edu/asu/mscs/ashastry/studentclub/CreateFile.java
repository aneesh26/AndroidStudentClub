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
 *          This module is used to set up offline files when server is unavailable
 *
 * @author : Aneesh Shastry  mailto:ashastry@asu.edu
 *           MS Computer Science, CIDSE, IAFSE, Arizona State University
 * @version : May 1, 2015
 */


import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by A on 5/1/2015.
 */


public class CreateFile  {


    private String filePath;
    private String fileName;
    private final Context context;

    public CreateFile(Context context, String fileName){
        this.context = context;
        filePath = context.getFilesDir().getPath()+"/";
        this.fileName = fileName;

        android.util.Log.d(this.getClass().getSimpleName(),"filepath: "+filePath);
    }

    public void doCreateFile(String fName) throws IOException {

        try {
            copyFiles(fName);
        } catch (IOException e) {
            android.util.Log.w(this.getClass().getSimpleName(),
                    "createDB Error copying database " + e.getMessage());
        }
    }


    private boolean checkFiles(){    //does the database exist and is it initialized?
        boolean ret = false;
            String path = filePath + fileName + ".json";
            android.util.Log.d(this.getClass().toString(),"Path to db : "+path);

            File aFile = new File(path);
            if(aFile.exists()){
                        ret = true;
                    }
        return ret;
        }


    public void copyFiles(String fName) throws IOException{
        try {
            if(!checkFiles()){
                InputStream ip = context.getResources().openRawResource(R.raw.events);

                if ("aboutus".equals(fName)) {
                    ip =  context.getResources().openRawResource(R.raw.aboutus);
                } else if ("events".equals(fName)) {
                    ip =  context.getResources().openRawResource(R.raw.events);
                } else if ("news".equals(fName)) {
                    ip =  context.getResources().openRawResource(R.raw.news);
                } else if ("faqs".equals(fName)) {
                    ip =  context.getResources().openRawResource(R.raw.faqs);
                }
  // make sure the database path exists. if not, create it.
                File aFile = new File(filePath);
                if(!aFile.exists()){
                    aFile.mkdirs();
                }
                String op=  filePath  +  fileName +".json";
                OutputStream output = new FileOutputStream(op);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = ip.read(buffer))>0){
                    output.write(buffer, 0, length);
                }
                output.flush();
                output.close();
                ip.close();
            }
        } catch (IOException e) {
            android.util.Log.w("CourseDB --> copyDB", "IOException: "+e.getMessage());
        }
    }



}
