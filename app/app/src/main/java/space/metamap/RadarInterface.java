package space.metamap;

import io.radar.sdk.Radar;
import io.radar.sdk.RadarReceiver;
import io.radar.sdk.RadarTrackingOptions;
import io.radar.sdk.model.RadarUser;

public class RadarInterface {

    RadarReceiver myRadarReciever = new MyRadarReciever();

    public RadarInterface(RadarUser radarUser) { }

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

}
