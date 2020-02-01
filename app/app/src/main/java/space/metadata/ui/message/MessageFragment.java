package space.metadata.ui.message;

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

import space.metadata.R;

public class MessageFragment extends Fragment {

	private MessageViewModel messageViewModel;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		messageViewModel =
			ViewModelProviders.of(this).get(MessageViewModel.class);
		View root = inflater.inflate(R.layout.fragment_message, container, false);
		final TextView textView = root.findViewById(R.id.text_notifications);
		messageViewModel.getText().observe(this, new Observer<String>() {
			@Override
			public void onChanged(@Nullable String s) {
				textView.setText(s);
			}
		});
		return root;
	}
}
