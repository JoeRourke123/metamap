package space.metamap.ui.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import space.metamap.R;

public class FeedFragment extends Fragment {

	private FeedViewModel feedViewModel;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		feedViewModel =
			ViewModelProviders.of(this).get(FeedViewModel.class);
		View root = inflater.inflate(R.layout.fragment_feed, container, false);
		final TextView textView = root.findViewById(R.id.text_home);
		feedViewModel.getText().observe(this, new Observer<String>() {
			@Override
			public void onChanged(@Nullable String s) {
				textView.setText(s);
			}
		});
		return root;
	}
}
