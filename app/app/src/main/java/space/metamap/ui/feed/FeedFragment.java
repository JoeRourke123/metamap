package space.metamap.ui.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import space.metamap.R;

public class FeedFragment extends Fragment {

	private FeedViewModel feedViewModel;
	private ArrayList feed;
	private ListView feedList;

	FeedFragment(ArrayList feed) {
		this.feed = feed;
	}

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {



		feedViewModel =
			ViewModelProviders.of(this).get(FeedViewModel.class);
		View root = inflater.inflate(R.layout.fragment_feed, container, false);
		return root;
	}
}
