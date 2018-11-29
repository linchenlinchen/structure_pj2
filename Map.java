package com.company;

import java.util.ArrayList;

public class Map {
    private Person person;
    private ArrayList<Vertex> vertices;
    private Destination destination;
    public Map(Person person,ArrayList<Vertex> vertices,Destination destination){
        this.person = person;
        this.vertices = vertices;
        this.destination = destination;
    }

    public Vertex nearest_station(){
        double man_long = person.getLongitude();
        double man_la = person.getLatitude();
        Vertex nearest = vertices.get(0);
        double distance = Math.sqrt(Math.pow(man_long-nearest.getLongitude(),2) + Math.pow(man_la-nearest.getLatitude(),2));
        for (Vertex vertex:vertices) {//每个站点逐一遍历，复杂度是V
            double station_long = vertex.getLongitude();
            double station_la = vertex.getLatitude();
            if(getDistance(vertex) < distance){
                distance = getDistance(vertex);
                nearest = vertex;
            }
        }
        return nearest;
    }

    public double getDistance(Vertex vertex){
        double man_long = person.getLongitude();
        double man_la = person.getLatitude();
        double distance = Math.sqrt(Math.pow(man_long-vertex.getLongitude(),2) + Math.pow(man_la-vertex.getLatitude(),2));
        return distance;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }
}
