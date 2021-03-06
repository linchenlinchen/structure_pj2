package com.company;

import java.util.ArrayList;

public class Vertex implements Comparable{
    private ArrayList<Vertex> adj = new ArrayList<Vertex>();
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private String name ;
    private double longitude;
    private double latitude;
    private Vertex pai = null;
    private int d = Integer.MAX_VALUE;

    public Vertex(String name,double longitude, double latitude){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    //获取线路名称，对于重叠的路线，将字符串连接返回
    public String getLine(Vertex side){
        String lineNames = "";
        for (Edge edge:this.getEdges()) {
            for (Edge edge1:side.getEdges()) {
                if(edge == edge1) lineNames = lineNames + " " + edge.getLine();
            }
        }
        return lineNames;
    }

    public Vertex getPai() {
        return pai;
    }

    public void setPai(Vertex pai) {
        this.pai = pai;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
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

    @Override
    public int compareTo(Object o) {
        Vertex v = (Vertex)o;
        if(this.getD() > v.getD()){
            return 1;
        }
        else if(this.getD() == v.getD()) {
            return 0;
        }
        return -1;
    }
}
