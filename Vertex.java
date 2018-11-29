package com.company;

import java.util.ArrayList;

public class Vertex {
    private ArrayList<Vertex> adj = new ArrayList<Vertex>();
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private String name ;
    private double longitude;
    private double latitude;

    public Vertex(String name,double longitude, double latitude){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void addEdges(Edge edge){
        edges.add(edge);
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public ArrayList<Vertex> getAdj() {
        return adj;
    }

    public void addAdj(Vertex vertex){
        adj.add(vertex);
    }

    public void setAdj(ArrayList<Vertex> adj) {
        this.adj = adj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
