package space.metamap.postelements;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.like.LikeButton;

import java.util.HashMap;

import space.metamap.Post;
import space.metamap.R;

public class PostElement extends Fragment {

    final Post post;
    final Location location;

    public PostElement(Post post, Location location) {
        this.post = post;
        this.location = location;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        feedViewModel =
//                ViewModelProviders.of(this).get(FeedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_feed, container, false);
        final TextView username = root.findViewById(R.id.username),
                        body = root.findViewById(R.id.body),
                        likes = root.findViewById(R.id.likes),
                        distance = root.findViewById(R.id.distance);

        final LikeButton liked = root.findViewById(R.id.liked);

        username.setText("from @" + post.getUsername());
        body.setText(post.getContent());
        likes.setText("42");
        distance.setText("Posted from " + post.getDistance(location) + "km away");
        liked.setLiked(savedInstanceState.getBoolean("liked"));

        HashMap<String, Integer> ids = new HashMap<>();
        ids.put("text", R.id.text);
        ids.put("image", R.id.image);
        ids.put("spotify", R.id.spotify);
        ids.put("youtube", R.id.youtube);

        (root.findViewById(ids.get(post.getType()))).setVisibility(View.VISIBLE);

        return root;
    }
}
