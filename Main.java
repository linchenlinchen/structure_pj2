package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    final static double meterPerDegree = 40076000.0/360.0;
    final static double walkSpeed = 5.0;
    public static void main(String[] args) {
        timeLeast();
        walkLeast();

        //最少换乘
    }

    public static void timeLeast(){
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        DataBuilder.loadMap("src/com/company/subway.xls",vertices);//初始化站点
        System.out.println("(时间最少)输入起点经纬度和终点经纬度：");
        Scanner input = new Scanner(System.in);
        double longitude = input.nextDouble();
        double latitude = input.nextDouble();
        double des_long = input.nextDouble();
        double des_la = input.nextDouble();
        Person person = new Person(longitude,latitude);//初始化人
        Destination destination = new Destination(des_long,des_la);
        Map map = new Map(person,vertices,destination);
        Vertex people = new Vertex("people",longitude,latitude);
        Vertex terminal = new Vertex("terminal",des_long,des_la);
        for (Vertex vertex:vertices) {
            double walkDistance1 = map.getDistance(vertex,"person");
            int walkTime1 = (int)(walkDistance1 * meterPerDegree/walkSpeed/60 + 1);
            double walkDistance2 = map.getDistance(vertex,"terminal");
            int walkTime2 = (int)(walkDistance2 * meterPerDegree/walkSpeed/60 + 1);
            DataBuilder.addEdge(people,vertex,"walk",walkTime1);
            DataBuilder.addEdge(vertex,terminal,"walk",walkTime2);
        }
        vertices.add(people);
        vertices.add(terminal);
        Main.same(map,people,terminal);
    }

    public static void walkLeast(){
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        DataBuilder.loadMap("src/com/company/subway.xls",vertices);//初始化站点
        System.out.println("\n(步行最少)输入起点经纬度和终点经纬度：");
        Scanner input = new Scanner(System.in);
        double longitude = input.nextDouble();
        double latitude = input.nextDouble();
        double des_long = input.nextDouble();
        double des_la = input.nextDouble();
        Person person = new Person(longitude,latitude);//初始化人
        Destination destination = new Destination(des_long,des_la);
        Map map = new Map(person,vertices,destination);

        //步行最少
        Vertex stop_person = map.nearest_station("person");//找到目前最近站点
        Vertex stop_dest = map.nearest_station("destination");
        Main.same(map,stop_person,stop_dest);
    }

    public static void changeLeast(){

    }

    public static void same(Map map,Vertex stop_person,Vertex stop_dest){
        map.dijkstra(stop_person);
        ArrayList<Vertex> back_road = new ArrayList<Vertex>();
        ArrayList<String> backLine = new ArrayList<String>();
        Vertex now = stop_dest;
        do {
            back_road.add(now);
            backLine.add(now.getLine(now.getPai()));
            now = now.getPai();
        }while (now != stop_person);
        back_road.add(now);
        int len = back_road.size();
        for (int i = 0; i < len; i++) {
            String line = "";
            if(backLine.size() > 0){
                line = backLine.get(backLine.size() - 1);
                String[] cut = line.split(" ");
                if(cut.length > 3 && cut[2].equals(cut[4])){
                    line = cut[1] + " " + cut[2];
                }
            }
            if(i != len - 1) {
                System.out.print(back_road.get(back_road.size() - 1).getName() + "-" + line + "->");
            }
            else{
                System.out.print(back_road.get(back_road.size() - 1).getName());
            }
            back_road.remove(back_road.size()-1);
            if(backLine.size() > 0) {
                backLine.remove(backLine.size() - 1);
            }
        }
    }
}
