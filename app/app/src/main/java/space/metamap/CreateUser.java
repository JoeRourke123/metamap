package space.metamap;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import space.metamap.util.Request;

public class CreateUser {

    public static void createUser(final Context context, final String username, final String password) {
        String url = "https://metamapp.herokuapp.com/signup";

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        try {
            Request jsonRequest = new Request(Request.Method.POST, url, new JSONObject(String.format("{\"username\": \"%s\", \"password\":\"%s\"}", username, password)), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response.has("error")) {
                            System.err.println(response.get("error"));
                        }
                        else {
                            System.out.println(response.get("message"));
                        }
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
