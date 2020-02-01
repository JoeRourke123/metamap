package space.metamap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.content.Context;
import org.json.JSONObject;
import org.json.*;
import java.util.HashMap;
import java.util.Map;

public class RecieveUser {

    public static void RecieveUser1(Context context, final String username, final String password) {
        String url = "https://metamapp.herokuapp.com/login";

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
                data.put("username", username);
                data.put("password", password);
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }


}
