package com.food.food_order_hotel_admin.Utils;

public class GeoPoint {
    double lat;
    double log;
    public GeoPoint(double v, double v1) {
            lat=v;
            log=v1;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }
}
