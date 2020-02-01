package space.metamap;

import android.location.Location;

import io.radar.sdk.model.RadarUser;

public class User {

    private String userName;
    private RadarUser radarUser;

    public User(String userName, String password) {

        // Get user data from API

        // Set the radar user later during initial location get
        radarUser = null;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public RadarUser getRadarUser() {
        return radarUser;
    }

    public void setRadarUser(RadarUser radarUser) {
        this.radarUser = radarUser;
    }







}
