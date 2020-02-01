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

public class SendPost {

    public static <T> void sendPost(Context context, T content, String username, Coordinate coordinate, String type, final PostList postList) {
        Post<T> post = new Post(content, username, coordinate, type);
        JSONObject object = new JSONObject();
        String msg = "";
        if(type.equals("text")) {
            msg = String.format("{\"operation\": \"add\", \"type\": \"%s\", \"data\": \"%s\", \"coordinates\": [%f, %f]}", type, content, coordinate.getLatitude(), coordinate.getLongitude());
        }
        if(type.equals("spotify")) {

        }
        if(type.equals("youtube")) {

        }
        if(type.equals("sound")) {

        }
        if(type.equals("image")) {

        }

        String url = "https://metamapp.herokuapp.com/post";

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        try {
            Request jsonRequest = new Request(Request.Method.POST, url, new JSONObject(msg), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray data = response.getJSONArray("posts");
                        for (int i=0; i<data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            Coordinate coordinate = new Coordinate(((double[])item.get("coordinate"))[0], ((double[])item.get("coordinate"))[1]);
                            postList.addToList(new Post(item.get("data"), (String) item.get("username"), coordinate, (String) item.get("type")));
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
