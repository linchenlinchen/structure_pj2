package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class Map {
    private Person person;
    private ArrayList<Vertex> vertices;
    private Destination destination;
    public Map(Person person,ArrayList<Vertex> vertices,Destination destination){
        this.person = person;
        this.vertices = vertices;
        this.destination = destination;
    }

    //根据字符串判断寻找离人最近站点或者离终点最近站点
    public Vertex nearest_station(String person_or_dest,Vertex except1,Vertex except2){
        double man_long = destination.getLongitude();
        double man_la = destination.getLatitude();
        if(person_or_dest.equals("person")) {
            man_long = person.getLongitude();
            man_la = person.getLatitude();
        }
        Vertex nearest = vertices.get(0);
        double distance = Math.sqrt(Math.pow(man_long-nearest.getLongitude(),2) + Math.pow(man_la-nearest.getLatitude(),2));
        for (Vertex vertex:vertices) {//每个站点逐一遍历，复杂度是V
            if(vertex != except1 && vertex != except2 && getDistance(vertex,person_or_dest) < distance){
                distance = getDistance(vertex,person_or_dest);
                nearest = vertex;
            }
        }
        return nearest;
    }

    public Vertex[] topThree_nearest(String person_or_dest){
        Vertex[] topThree = new Vertex[3];
        topThree[0] = this.nearest_station(person_or_dest,null,null);
        topThree[1] = this.nearest_station(person_or_dest,topThree[0],null);
        topThree[2] = this.nearest_station(person_or_dest,topThree[1],topThree[0]);
        return topThree;
    }

    //Dijkstra算法返回从起点站到终点站的一条最短地铁线路
    public ArrayList<Vertex> dijkstra(Vertex begin){
        for (Vertex vertex:vertices) {
            vertex.setD(Integer.MAX_VALUE);
            vertex.setPai(null);
        }
        long start = System.nanoTime();
        begin.setD(0);
        ArrayList<Vertex> road = new ArrayList<Vertex>();
        while (vertices.size() != 0){
            Vertex u = extract_min();//可以用最小堆优化
            road.add(u);
            for (Vertex v:u.getAdj()) {
                if(v.getD() > u.getD() + findEdge(u,v).getCost() && u.getD() + findEdge(u,v).getCost() > 0){
                    v.setD(u.getD() + findEdge(u,v).getCost());
                    v.setPai(u);
                }
            }
        }
        long end = System.nanoTime();
        System.out.println("dijkstra use time : "+(end-start)/1000000 + "ms");
//        for (Vertex v:road) {
//            System.out.println(v.getName() + " "+v.getD());
//        }
        return road;
    }

    //由于站点不多，没必要用最小堆。最小堆还需要初始化维护之类的
    private Vertex extract_min(){
        Vertex minVertex = vertices.get(0);
        int min = vertices.get(0).getD();
        for (Vertex vertex:vertices) {
            if(vertex.getD()< min){
                minVertex = vertex;
                min = vertex.getD();
            }
        }
        vertices.remove(minVertex);
        return minVertex;
    }

    public Edge findEdge(Vertex begin,Vertex end){
        for (Edge edge1:begin.getEdges()) {
            for (Edge edge2:end.getEdges()) {
                if(edge1 == edge2)
                    return edge1;
            }
        }
        return null;
    }

    public double getDistance(Vertex vertex,String person_dest){
        double man_long = destination.getLongitude();
        double man_la = destination.getLatitude();
        if(person_dest.equals("person")) {
            man_long = person.getLongitude();
            man_la = person.getLatitude();
        }
        double distance = Math.sqrt(Math.pow(man_long-vertex.getLongitude(),2) + Math.pow(man_la-vertex.getLatitude(),2));
        return distance;
    }

    public void clearPai(){
        for (Vertex v:vertices) {

        }
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
