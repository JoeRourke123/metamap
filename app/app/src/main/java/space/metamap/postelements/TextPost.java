package space.metamap.postelements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.like.LikeButton;

import space.metamap.R;

public class TextPost extends Fragment {

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

        username.setText("from @" + savedInstanceState.getString("username"));
        body.setText(savedInstanceState.getString("body"));
        likes.setText(savedInstanceState.getString("likes"));
        distance.setText("Posted from " + savedInstanceState.getString("distance" + "km away"));
        liked.setLiked(savedInstanceState.getBoolean("liked"));

        return root;
    }
}
