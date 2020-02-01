package space.metamap;

import io.radar.sdk.model.Coordinate;

public class Post<T> {

    private T content;
    private String username;
    private Coordinate coordinate;
    private String type;


    public Post(T content, String username, Coordinate coordinate, String type) {
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

    public T getContent() {
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
