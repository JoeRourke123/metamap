package space.metamap;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import io.radar.sdk.model.Coordinate;
import space.metamap.util.Request;

public class SendPost {

    public static <T> void sendPost(Context context, final T content, String username, Coordinate coordinate, String type, final PostList postList) {
        String msg = "";
        switch (type) {
            case "text":
            case "youtube":
            case "spotify":
                msg = String.format("{\"operation\": \"add\", \"type\": \"%s\", \"data\": \"%s\", \"coordinates\": [%f, %f]}", type, content, coordinate.getLatitude(), coordinate.getLongitude());
                break;
        }

        String url = "https://metamapp.herokuapp.com/post";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        if(!type.equals("image") && !type.equals("sound")) {
            try {
                Request jsonRequest = new Request(Request.Method.POST, url, new JSONObject(msg), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("posts");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject item = data.getJSONObject(i);
                                Coordinate coordinate = new Coordinate(((double[]) item.get("coordinate"))[0], ((double[]) item.get("coordinate"))[1]);
                                postList.addToList(new Post((String) item.get("data"), (String) item.get("username"), (String) item.get("type"), coordinate));
                            }
                        } catch (JSONException e) {
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
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        if(type.equals("image")) {
            try {
                VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                try {
                                    JSONObject obj = new JSONObject(new String(response.data));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.err.println(error);
                            }
                        }) {

                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, DataPart> params = new HashMap<>();
                        long imagename = System.currentTimeMillis();
                        params.put("data", new DataPart(imagename + ".jpg", getFileDataFromDrawable((Bitmap) content)));
                        return params;
                    }
                };
                requestQueue.add(volleyMultipartRequest);
            } catch (Exception e) {
                System.err.println(e);
            }
        }

    }
    public static byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
