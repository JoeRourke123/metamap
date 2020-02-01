package space.metamap.ui.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import space.metamap.Post;
import space.metamap.R;

public class FeedFragment extends Fragment {

	private FeedViewModel feedViewModel;
	private ListView feedList;
	private ArrayAdapter<Fragment> adapter;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {

		ArrayList<Post> posts = savedInstanceState.getParcelableArrayList("posts");
		adapter=new ArrayAdapter<Fragment>(container.getContext(), R.layout.post, (List) posts);
		feedList.setAdapter(adapter);
		View root = inflater.inflate(R.layout.fragment_feed, container, false);

		return root;
	}
}
