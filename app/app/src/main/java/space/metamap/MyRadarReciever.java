package space.metamap;

import android.content.Context;
import android.location.Location;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.radar.sdk.Radar;
import io.radar.sdk.Radar.RadarStatus;
import io.radar.sdk.RadarReceiver;
import io.radar.sdk.model.RadarEvent;
import io.radar.sdk.model.RadarUser;


public class MyRadarReciever extends RadarReceiver {

    private PostList postList;
    private Location currentLocation;

    public MyRadarReciever(PostList postList) {
        this.postList = postList;
        Radar.updateLocation(currentLocation, new Radar.RadarCallback() {
            @Override
            public void onComplete(@NotNull RadarStatus radarStatus, @Nullable Location location, @Nullable RadarEvent[] radarEvents, @Nullable RadarUser radarUser) {

            }
        });
    }

    @Override
    public void onEventsReceived(Context context, RadarEvent[] events, RadarUser user) {
        // do something with context, events, user
    }

    @Override
    public void onLocationUpdated(Context context, Location location, RadarUser user) {
        // do something with context, location, user
        RecievePosts.receivePost(context, location.getLatitude(), location.getLongitude(), this.postList);
        currentLocation = location;
    }

    @Override
    public void onError(Context context, RadarStatus status) {
        // do something with context, status
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

}
