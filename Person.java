package com.company;

public class Person {

    private double longitude;
    private double latitude;
    private Vertex nearest_station;
    public Person(double longitude,double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Vertex getNearest_station() {
        return nearest_station;
    }

    public void setNearest_station(Vertex nearest_station) {
        this.nearest_station = nearest_station;
    }
}
