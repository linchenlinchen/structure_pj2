package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<String>[][] line_list = new ArrayList[3][3];
    private static ArrayList<String>[][] choice = new ArrayList[3][3];
    private final static double meterPerDegree = 40076000.0/360.0;
    private final static double walkSpeed = 5.0;
    public static void main(String[] args) {
        timeLeast();
        walkLeast();
        initiation();
        changeLeast();
        //最少换乘
    }

    private static void initiation(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                line_list[i][j] = new ArrayList<String>();
                choice[i][j] = new ArrayList<String>();
            }
        }
    }

    private static void timeLeast(){
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
        Main.same(map,people,terminal,false,0,0);
    }

    private static void walkLeast(){
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
        Vertex stop_person = map.nearest_station("person",null,null);//找到目前最近站点
        Vertex stop_dest = map.nearest_station("destination",null,null);
        Main.same(map,stop_person,stop_dest,false,0,0);
    }

    private static void changeLeast(){
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        ArrayList<Vertex> recorder1 = new ArrayList<Vertex>();
        ArrayList<Vertex> recorder2 = new ArrayList<Vertex>();
        DataBuilder.loadMap("src/com/company/subway.xls",vertices);//初始化站点
        for (Vertex vertex:vertices) {
            recorder1.add(vertex);
            recorder2.add(vertex);
        }
        System.out.println("\n(换乘最少)输入起点经纬度和终点经纬度：");
        Scanner input = new Scanner(System.in);
        double longitude = input.nextDouble();
        double latitude = input.nextDouble();
        double des_long = input.nextDouble();
        double des_la = input.nextDouble();
        Person person = new Person(longitude,latitude);//初始化人
        Destination destination = new Destination(des_long,des_la);
        Map map = new Map(person,vertices,destination);

        Vertex[] topThree_person = map.topThree_nearest("person");
        Vertex[] topThree_terminal = map.topThree_nearest("terminal");
        int[][] changeLineNumber = new int[3][3];
        int i = -1,j = -1;
        for (Vertex person_stop:topThree_person) {
            i++;
            for (Vertex terminal_stop:topThree_terminal) {
                j++;
                same(map, person_stop, terminal_stop,true,i,j);
            }
            j = -1;
            //重新设置节点集
            if(recorder1.size() > 0) {
                map.setVertices(recorder1);
            }
            else{
                map.setVertices(recorder2);
            }
        }
        int temp = Integer.MAX_VALUE;
        int[] best = new int[2];
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if(temp > line_list[k][l].size()){
                    best[0] = k;
                    best[1] = l;
                    temp = line_list[k][l].size();
                }
            }
        }
        System.out.println("\n最短换乘：");
        for (String line:choice[best[0]][best[1]]) {
            System.out.print(line + "-");
        }
        System.out.print("destination");

    }

    private static void same(Map map, Vertex stop_person, Vertex stop_dest, boolean useLineList, int start, int end){
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
                String name = back_road.get(back_road.size() - 1).getName();
                System.out.print(name + "-" + line + "->");
                if(useLineList) {
                    choice[start][end].add(name);
                    choice[start][end].add(line);
                    if (line_list[start][end].size() == 0 ||
                            line_list[start][end].size() > 0 && !line_list[start][end].get(line_list[start][end].size() - 1).equals(line)) {
                        line_list[start][end].add(line);
                    }
                }
            }
            else{
                System.out.print(back_road.get(back_road.size() - 1).getName());
                if(useLineList) {
                    choice[start][end].add(back_road.get(back_road.size() - 1).getName());
                }
            }

            back_road.remove(back_road.size()-1);
            if(backLine.size() > 0) {
                backLine.remove(backLine.size() - 1);
            }
        }
    }
}
