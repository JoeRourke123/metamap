package space.metamap;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.widget.ArrayAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BaseHttpStack;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.radar.sdk.model.Coordinate;
import space.metamap.postelements.PostElement;
import space.metamap.util.Request;

import static android.content.Context.MODE_PRIVATE;

class RecievePosts extends Thread {
    private final Context context;
    private final double latitude, longitude;
    private final PostList postList;
    private final ArrayAdapter<PostElement> adapter;

    public RecievePosts(final Context context, final double latitude, final double longitude, final PostList postList, final ArrayAdapter<PostElement> adapter) {
        this.context = context;
        this.longitude = longitude;
        this.latitude = latitude;
        this.postList = postList;
        this.adapter = adapter;
    }

    public void run() {
        String url = "https://metamapp.herokuapp.com/post";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        RequestFuture<JSONObject> future = RequestFuture.newFuture();

        try {
            Request jsonRequest = new Request(Request.Method.POST, url, new JSONObject(String.format("{\"operation\": \"get\",\"coordinates\": [%f, %f]}", latitude, longitude)), future, future) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    SharedPreferences preferences = context.getSharedPreferences("metamapp", MODE_PRIVATE);

                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Cookie", "flask_sess=" + preferences.getString("session", "").split(";")[0]);
                    return headers;
                }
            };
            requestQueue.add(jsonRequest);
        } catch (JSONException e) {
            System.err.println(e);
        }

        try {
            JSONArray response = future.get(20, TimeUnit.SECONDS).getJSONArray("data");

            for(int i = 0; i < response.length(); i++) {
                Coordinate coord = new Coordinate(
                        (Double) response.getJSONObject(i).getJSONObject("location").getJSONArray("coordinates").get(0),
                        (Double) response.getJSONObject(i).getJSONObject("location").getJSONArray("coordinates").get(1)
                );

                Post post = new Post((String) response.getJSONObject(i).get("type"), (String) response.getJSONObject(i).get("data"), (String) response.getJSONObject(i).get("username"), coord);
                postList.addToList(post);
                Location location = new Location(LocationManager.GPS_PROVIDER);
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                adapter.add(new PostElement(post, location));

            }

            Thread.yield();
        } catch(JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException | TimeoutException e) {
            System.err.println(e);
        } catch (ExecutionException e) {
            System.err.println(e);
        }
    }
}
