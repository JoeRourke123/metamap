package space.metamap.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LocationInterface {

    private FusedLocationProviderClient locationProvider;
    private Context context;
    private Activity activity;

    public LocationInterface(Context context, Activity main) {
        this.context = context;
        this.activity = main;
        this.locationProvider = LocationServices.getFusedLocationProviderClient(context);
    }

    public void requestPermission() {
    }

    public Location getLastLocation() {
        requestPermission();

        final Location currentLocation = new Location(LocationManager.GPS_PROVIDER);

        if(ActivityCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        locationProvider.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                currentLocation.set(location);
            }
        });

        return currentLocation;
    }
}