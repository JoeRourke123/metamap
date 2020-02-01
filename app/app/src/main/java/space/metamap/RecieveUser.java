package space.metamap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import space.metamap.util.Request;

public class RecieveUser {

    public static void receiveUser(final Context context, final String username, final String password) {
        String url = "https://metamapp.herokuapp.com/login";

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        try {
            Request jsonRequest = new Request(Request.Method.POST, url, new JSONObject(String.format("{\"username\": \"%s\", \"password\":\"%s\"}", username, password)), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject data = response.getJSONObject("data");
                        JSONObject headers = response.getJSONObject("headers");

                        SharedPreferences.Editor prefs = context.getSharedPreferences("metamapp", context.MODE_PRIVATE).edit();
                        prefs.putString("session", headers.get("Set-Cookie").toString().split("=")[1]);
                        prefs.apply();

                        context.startActivity(new Intent(context, space.metamap.MainActivity.class));

                    } catch(JSONException e) {
                        System.err.println(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.toString());
                }
            });

            requestQueue.add(jsonRequest);
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
}
