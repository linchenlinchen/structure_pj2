package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        DataBuilder.loadMap("src/com/company/subway.xls",vertices);//初始化站点

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
        map.dijkstra(stop_person,stop_dest);
        ArrayList<Vertex> back_road = new ArrayList<Vertex>();
        Vertex now = stop_dest;
        do {
            back_road.add(now);
            now = now.getPai();
        }while (now != stop_person);
        back_road.add(now);
        int len = back_road.size();
        for (int i = 0; i < len; i++) {
            System.out.print(back_road.get(back_road.size()-1).getName() + "->");
            back_road.remove(back_road.size()-1);
        }

        //最少换乘
    }
}
