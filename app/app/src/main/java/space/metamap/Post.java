package space.metamap;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import io.radar.sdk.model.Coordinate;

public class Post implements Parcelable {

    String content;
    String username;
    Coordinate coordinate;
    String type;

    public Post(String type, String content, String username, Coordinate coordinate) {
        this.username = username;
        this.content = content;
        this.coordinate = coordinate;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Post(Parcel source) {
        content = source.readString();
        username = source.readString();
        coordinate = new Coordinate(source.readDouble(), source.readDouble());
        type = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(username);
        dest.writeDouble(coordinate.getLatitude());
        dest.writeDouble(coordinate.getLongitude());
        dest.writeString(type);
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public String getDistance(Location location) {
        return String.valueOf(distance(this.coordinate.getLatitude(), this.coordinate.getLongitude(), location.getLatitude(), location.getLongitude()));
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

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }

        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }
    };
}
