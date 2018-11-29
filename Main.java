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
        Map map = new Map(person,vertices);

        //步行最少
        Vertex nearest_station = map.nearest_station();//找到目前最近站点
    }
}
