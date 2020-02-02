package space.metamap;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import space.metamap.util.LocationInterface;

public class PostList implements Parcelable {

    private ArrayList<Post> list;

    public PostList() {
        this.list = new ArrayList<>();
    }

    public PostList(Parcel parcel) {
        parcel.readList(list, Post.class.getClassLoader());
    }

    public void getRetrieveList(Context context, double latitude, double longitude) {
        RecievePosts.receivePost(context, latitude, longitude, this);
    }

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
