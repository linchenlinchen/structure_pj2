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

    //根据字符串判断寻找离人最近站点或者离终点最近站点
    public Vertex nearest_station(String person_or_dest){
        double man_long = destination.getLongitude();
        double man_la = destination.getLatitude();
        if(person_or_dest.equals("person")) {
            man_long = person.getLongitude();
            man_la = person.getLatitude();
        }
        Vertex nearest = vertices.get(0);
        double distance = Math.sqrt(Math.pow(man_long-nearest.getLongitude(),2) + Math.pow(man_la-nearest.getLatitude(),2));
        for (Vertex vertex:vertices) {//每个站点逐一遍历，复杂度是V
            if(getDistance(vertex,person_or_dest) < distance){
                distance = getDistance(vertex,person_or_dest);
                nearest = vertex;
            }
        }
        return nearest;
    }

    //Dijkstra算法返回从起点站到终点站的一条最短地铁线路
    public ArrayList<Vertex> dijkstra(Vertex begin){
        long start = System.nanoTime();
        begin.setD(0);
        ArrayList<Vertex> road = new ArrayList<Vertex>();
        while (vertices.size() != 0){
            Vertex u = extract_min();//可以用最小堆优化
            road.add(u);
            for (Vertex v:u.getAdj()) {
                if(v.getD() > u.getD() + findEdge(u,v).getCost()){
                    v.setD(u.getD() + findEdge(u,v).getCost());
                    v.setPai(u);
                }
            }
        }
        long end = System.nanoTime();
        System.out.println("dijkstra use time : "+(end-start)/1000000 + "ms");
        for (Vertex v:road) {
            System.out.println(v.getName() + " "+v.getD());
        }
        return road;
    }

    //由于站点不多，没必要用最小堆。最小堆还需要初始化维护之类的
    public Vertex extract_min(){
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
