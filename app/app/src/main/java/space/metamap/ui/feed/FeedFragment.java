package space.metamap.ui.feed;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import space.metamap.MainActivity;
import space.metamap.Post;
import space.metamap.PostList;
import space.metamap.R;
import space.metamap.postelements.PostElement;
import space.metamap.util.LocationInterface;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class FeedFragment extends Fragment {

	private FeedViewModel feedViewModel;
	private ListView feedList;
	private ArrayAdapter<PostElement> adapter;
	private TextView coords;
	private final Location location = new Location(LocationManager.GPS_PROVIDER);;

	public void setTextField(Location l) {
		coords.setText(String.format("%f, %f", l.getLongitude(), l.getLatitude()));
	}

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, final Bundle savedInstanceState) {
		final View root = inflater.inflate(R.layout.fragment_feed, container, false);
		MainActivity mainActivity = (MainActivity) getActivity();

		final PostList posts = mainActivity.getPosts();
		feedList = root.findViewById(R.id.feedList);


		FusedLocationProviderClient locationProvider = LocationServices.getFusedLocationProviderClient(getContext());

		ActivityCompat.requestPermissions(getActivity(), new String[] { ACCESS_FINE_LOCATION }, 1);

		if(ActivityCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			return null;
		}
		locationProvider.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
			@Override
			public void onSuccess(Location loc) {
				coords = root.findViewById(R.id.coords);
				setTextField(loc);
				location.set(loc);
				adapter = new ArrayAdapter<>(getContext(), R.layout.post);
				posts.getRetrieveList(getContext(), loc.getLatitude(), loc.getLongitude(), adapter);

				System.out.println("HHHHHHHHHHHHHHHHHHHH" + posts.getList().size());
			}
		});

		locationProvider.requestLocationUpdates(LocationRequest.create(), new LocationCallback() {
			@Override
			public void onLocationResult(LocationResult locationResult) {
				if (locationResult == null) {
					return;
				}
				for (Location loc : locationResult.getLocations()) {
					location.set(loc);
					setTextField(loc);
				}
			};
		}, Looper.getMainLooper());

		return root;
	}
}
