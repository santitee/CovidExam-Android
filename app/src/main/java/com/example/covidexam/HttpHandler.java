package com.example.covidexam;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHandler {
    private static final String TAG = HttpHandler.class.getSimpleName();
    
    public String makerServiceCall(String reqUrl) {
        String response = null;
        try{
            URL url = new URL(reqUrl);
            HttpURLConnection conn=(HttpURLConnection) url.openConnection(); 
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response=converseStreamToString(in);
        }catch (Exception e) {
            Log.e(TAG,"Exception" + e.getMessage());
        }
        return  response;
    } // end method makerServiceCall

    private String converseStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb= new StringBuilder();
        String line;
        try{
            while ((line = reader.readLine()) !=null) {
                sb.append(line).append('\n');
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    } // end method converseStreamToString
} // end class 
