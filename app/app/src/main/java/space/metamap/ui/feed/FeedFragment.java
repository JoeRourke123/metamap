package space.metamap.ui.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import space.metamap.Post;
import space.metamap.PostList;
import space.metamap.R;
import space.metamap.postelements.PostElement;

public class FeedFragment extends Fragment {

	private FeedViewModel feedViewModel;
	private ListView feedList;
	private ArrayAdapter<PostElement> adapter;
	private TextView coords;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_feed, container, false);

		PostList posts = new PostList();

		coords = root.findViewById(R.id.coords);

		feedList = root.findViewById(R.id.feedList);
		adapter = new ArrayAdapter<>(container.getContext(), R.layout.post);

		for(Post post : posts.getList()) {
			adapter.add(new PostElement(post));
		}

		feedList.setAdapter(adapter);

		return root;
	}
}
