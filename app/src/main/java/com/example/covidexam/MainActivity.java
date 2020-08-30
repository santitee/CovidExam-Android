package com.example.covidexam;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private String TAG=MainActivity.class.getSimpleName();
    private ListView lv;

    // get Json From URL
    private static  String url="https://coronavirus-19-api.herokuapp.com/countries";
    ArrayList<HashMap<String,String>>contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        lv=(ListView)findViewById(R.id.list);

        new GetContact().execute();
    } // end on create

    private class GetContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0){
            HttpHandler sh=new HttpHandler();
            // call url from line16
            String jsonStr = sh.makerServiceCall(url);
            Log.e(TAG,"Response from url:" +jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray mj = new JSONArray(jsonStr);
                    for (int i =0; i< mj.length(); i++){
                        JSONObject mjo = mj.getJSONObject(i);
                        String country = mjo.getString("country");
                        String todayCases = mjo.getString("todayCases");
                        String deaths = mjo.getString("deaths");
                        String todayDeaths = mjo.getString("todayDeaths");

                        HashMap<String, String> contact = new HashMap<>();
                        contact.put("country", "ประเทศ : " +country);
                        contact.put("todayCases", "จำนวนผู้ติดชื้อทั้งหมด : "+todayCases);
                        contact.put("deaths", "จำนวนผู้ตเสียชีวิต: "+deaths);
                        contact.put("todayDeaths", "จำนวนผู้ตสียชีวิตตวันนี้ : "+todayDeaths);

                        contactList.add(contact);

                    } // end for

                } catch (Exception e) {

                }// end catch
            }else {
                Log.e(TAG,"Couldn't Get Json from server");
            } // end if-else
            return null;
        } // end doInBackground
    @Override
    protected void onPostExecute(Void results) {
        ListAdapter adapter = new SimpleAdapter(MainActivity.this,contactList,R.layout.list_item,
                new String[]{"country", "todayCases", "deaths", "todayDeaths"},
                new int[]{R.id.country, R.id.todayCases, R.id.deaths, R.id.todayDeaths});
        lv.setAdapter(adapter);
        }

    } // end GetContact
} // end class