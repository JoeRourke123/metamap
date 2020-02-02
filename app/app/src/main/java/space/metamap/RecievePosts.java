package space.metamap;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.radar.sdk.model.Coordinate;
import space.metamap.postelements.PostElement;
import space.metamap.util.Request;

import static android.content.Context.MODE_PRIVATE;

public class RecievePosts extends Thread {

    private final Context context;
    private final double latitude, longitude;
    private final PostList postList;
    //private JSONObject resp;
    private ListView feedList;

    public RecievePosts(PostList postList, final Context context, double latitude, double longitude, ListView feedList) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.context = context;
        this.postList = postList;
        this.feedList = feedList;
    }

    public void run() {
        String url = "https://metamapp.herokuapp.com/post";

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        try {
            Request jsonRequest = new Request(Request.Method.POST, url, new JSONObject(String.format("{\"operation\": \"get\",\"coordinates\": [%f, %f]}", latitude, longitude)), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject res) {
                    System.out.println("DOES THIS WORK: " + res);
                    JSONArray posts = new JSONArray();
                    try {
                        posts = res.getJSONObject("data").getJSONArray("posts");
                        for(int i = 0; i < posts.length(); i++) {
                            String tempType = ((JSONObject)posts.get(i)).getString("type");
                            String tempContent = ((JSONObject)posts.get(i)).getString("data");
                            String tempUsername = ((JSONObject)posts.get(i)).getString("username");
                            Coordinate tempCoordinate  = new Coordinate((double)((JSONObject)posts.get(i)).getJSONObject("location").getJSONArray("coordinates").get(0), (double)((JSONObject)posts.get(i)).getJSONObject("location").getJSONArray("coordinates").get(1));
                            postList.addToList(new Post(tempType, tempContent, tempUsername, tempCoordinate));
                        }
                    }catch (JSONException e) {
                        System.err.println(e);
                    }


                    ArrayList<PostElement> postElements = new ArrayList<>();
                    for(Post post : postList.getList()) {
                        Location temp = new Location("");
                        temp.setLongitude(post.getCoordinate().getLongitude());
                        temp.setLatitude(post.getCoordinate().getLatitude());
                        PostElement postElement =  new PostElement(post, temp);
                        postElements.add(postElement);
                    }
                    ArrayAdapter<PostElement> arrayAdapter = new ArrayAdapter<PostElement>(context, android.R.layout.simple_list_item_1, postElements);
                    feedList.setAdapter(arrayAdapter);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
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

        Thread.yield();
    }
}
