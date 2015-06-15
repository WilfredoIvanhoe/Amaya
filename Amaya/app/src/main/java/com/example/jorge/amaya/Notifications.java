package com.example.jorge.amaya;

import android.app.DownloadManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Notifications extends ActionBarActivity {

    TextView notifications_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        notifications_txt = (TextView)findViewById(R.id.notifications_txt);

            String url = "http://amaya-security.herokuapp.com/notifications/1";

            RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray nots;
                        try {
                            nots = new JSONArray(response);
                            if (nots.length() == 0) {
                                notifications_txt.setText("No hay notificaciones");
                            } else {
                                for (int i = 0; i < nots.length(); i++) {
                                    notifications_txt.setText(notifications_txt.getText() + "\nArduino " + nots.getJSONObject(i).get("arduino_id") + ": " + nots.getJSONObject(i).get("time"));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                notifications_txt.setText(error.toString());
            }
        }){
            @Override
            public String getBodyContentType(){
                return "application/json";
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notifications, menu);
        return true;
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
