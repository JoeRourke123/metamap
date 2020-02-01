package space.metamap;

import android.os.Parcel;
import android.os.Parcelable;

import io.radar.sdk.model.Coordinate;

public class Post implements Parcelable {

    String type
    String content;
    String username;
    Coordinate coordinate;

    public Post(String type, String content, String username, Coordinate coordinate) {
        this.type = type;
        this.username = username;
        this.content = content;
        this.coordinate = coordinate;
    }

    public Post(Parcel source) {
        content = source.readString();
        username = source.readString();
        coordinate = new Coordinate(source.readDouble(), source.readDouble());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(username);
        dest.writeDouble(coordinate.getLatitude());
        dest.writeDouble(coordinate.getLongitude());
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public String getDistance(Coordinate coordinate) {
        return String.valueOf(distance(this.coordinate.getLatitude(), this.coordinate.getLongitude(), coordinate.getLatitude(), coordinate.getLongitude()));
    }
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
