package space.metamap;

import android.content.Context;

import java.util.ArrayList;

public class PostList {

    private ArrayList<Post> list;

    public PostList(Context context, double latitude, double longitude) {
        this.list = new ArrayList<>();
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
}
