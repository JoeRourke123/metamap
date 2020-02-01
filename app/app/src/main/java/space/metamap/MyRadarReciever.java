package space.metamap;

import android.content.Context;
import android.location.Location;

import io.radar.sdk.Radar.RadarStatus;
import io.radar.sdk.RadarReceiver;
import io.radar.sdk.model.RadarEvent;
import io.radar.sdk.model.RadarUser;


public class MyRadarReciever extends RadarReceiver {

    private PostList postList;

    public MyRadarReciever(PostList postList) {
        this.postList = postList;
    }

    @Override
    public void onEventsReceived(Context context, RadarEvent[] events, RadarUser user) {
        // do something with context, events, user
    }

    @Override
    public void onLocationUpdated(Context context, Location location, RadarUser user) {
        // do something with context, location, user
        RecievePost.receivePost(context, location.getLatitude(), location.getLongitude(), this.postList);
    }

    @Override
    public void onError(Context context, RadarStatus status) {
        // do something with context, status
    }

}
