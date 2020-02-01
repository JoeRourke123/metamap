package space.metamap;

import android.location.Location;

import io.radar.sdk.Radar;
import io.radar.sdk.RadarTrackingOptions;

public class RadarInterface {
    MyRadarReciever myRadarReciever;

    public RadarInterface(PostList postList) {
        this.myRadarReciever = new MyRadarReciever(postList);
    }

    public void runTrack() {
        RadarTrackingOptions trackingOptions = new RadarTrackingOptions.Builder()
                .priority(Radar.RadarTrackingPriority.RESPONSIVENESS)
                .offline(Radar.RadarTrackingOffline.REPLAY_STOPPED)
                .sync(Radar.RadarTrackingSync.POSSIBLE_STATE_CHANGES)
                .build();

        Radar.startTracking(trackingOptions);
    }

    public void stopTrack() {
        Radar.stopTracking();
    }

    public Location getLocation() {
        return this.myRadarReciever.getCurrentLocation();
    }
}
