package space.metamap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.location.Location;
import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class RecievePosts {

    public static void get(final Location location, Context context) {
        String url = "https://metamapp.herokuapp.com/post";

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response.substring(0, 500));
                try {
                    JSONObject json = new JSONObject(response);
                    System.out.println(json.getString("body"));
                }
                catch(JSONException e) {
                    System.out.println(e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("latitude", String.valueOf(location.getLatitude()));
                data.put("longitude", String.valueOf(location.getLongitude()));
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

}
