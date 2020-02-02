package space.metamap;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.radar.sdk.model.Coordinate;
import space.metamap.postelements.PostElement;
import space.metamap.util.LocationInterface;

public class PostList implements Parcelable {

    private ArrayList<Post> list;

    public PostList() {
        this.list = new ArrayList<>();
    }

    public PostList(Parcel parcel) {
        parcel.readList(list, Post.class.getClassLoader());
    }
/*
    public void getRetrieveList(Context context, double latitude, double longitude, ArrayAdapter<PostElement> adapter) {
        RecievePosts response = new RecievePosts(context, latitude, longitude, this, adapter);
        response.run();
    }

 */

    public ArrayList<Post> getList() {
        return list;
    }

    private void setList(ArrayList<Post> list) {
        this.list = list;
    }

    public void addToList(Post post) {
        if(!this.list.contains(post)) {
            this.list.add(post);
        }
    }

    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelableList(list, flags);
    }

    public static final Creator<PostList> CREATOR = new Creator<PostList>() {
        @Override
        public PostList[] newArray(int size) {
            return new PostList[size];
        }

        @Override
        public PostList createFromParcel(Parcel source) {
            return new PostList(source);
        }
    };

}
