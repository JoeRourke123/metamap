package space.metamap.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import space.metadata.R;

public class MapFragment extends Fragment implements OnMapReadyCallback {
	public MapFragment() {
		// Required empty public constructor
	}
	private MapViewModel mapViewModel;
	private GoogleMap map;

	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_map, container, false);
		return root;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		if(getActivity()!=null) {
			SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
				.findFragmentById(R.id.map);
			if (mapFragment != null) {
				mapFragment.getMapAsync(this);
			}
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		map = googleMap;

		//Do your stuff here
	}
}
