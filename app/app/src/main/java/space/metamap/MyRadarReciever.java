package space.metamap;

import io.radar.sdk.RadarReceiver;
import io.radar.sdk.model.RadarEvent;
import io.radar.sdk.model.RadarUser;
import io.radar.sdk.Radar.RadarStatus;
import android.content.Context;
import android.location.Location;


public class MyRadarReciever extends RadarReceiver {

    @Override
    public void onEventsReceived(Context context, RadarEvent[] events, RadarUser user) {
        // do something with context, events, user
    }

    @Override
    public void onLocationUpdated(Context context, Location location, RadarUser user) {
        // do something with context, location, user
    }

    @Override
    public void onError(Context context, RadarStatus status) {
        // do something with context, status
    }

}
