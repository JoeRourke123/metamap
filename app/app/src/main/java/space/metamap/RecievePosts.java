package space.metamap;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.radar.sdk.model.Coordinate;
import space.metamap.util.Request;

class RecievePosts {

    public static void receivePost(final Context context, final double latitude, final double longitude, final PostList postList) {
        String url = "https://metamapp.herokuapp.com/post";

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        try {
            Request jsonRequest = new Request(Request.Method.POST, url, new JSONObject(String.format("{\"operation\": \"get\",\"coordinates\": \"[%f, %f]\"}", latitude, longitude)), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray data = response.getJSONArray("posts");
                        for (int i=0; i<data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            System.out.println(item.toString());
                            Coordinate coordinate = new Coordinate(((double[])item.get("coordinate"))[0], ((double[])item.get("coordinate"))[1]);
                            postList.addToList(new Post((String) item.get("type"), (String) item.get("data"), (String) item.get("username"), coordinate));
                        }
                    } catch(JSONException e) {
                        System.err.println(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.err.println(error.toString());
                }
            });
            requestQueue.add(jsonRequest);
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }
}
