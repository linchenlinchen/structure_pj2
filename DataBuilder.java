package com.company;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DataBuilder {
    public static void loadMap(String path, ArrayList<Vertex> vertices) {
        File input = new File(path);
        try {
            Workbook book = Workbook.getWorkbook(input);
            int sheetSize = book.getNumberOfSheets();
            for(int i = 0; i < sheetSize; i++) {
                System.out.println(i + 1);
                //每个sheet 获取几号线
                Sheet sheet = book.getSheet(i);
                String line = sheet.getName();
                int columns = sheet.getColumns();//表格列数
                int rows = sheet.getRows();//表格行数
                //先加入所有的站,对于已经加入的站，不加入
                for(int row = columns-3; row < rows; row++) {
                    String name = sheet.getCell(0, row).getContents();
                    double longitude = Double.parseDouble(sheet.getCell(1, row).getContents());
                    double latitude = Double.parseDouble(sheet.getCell(2, row).getContents());
                    exist(vertices,name,longitude, latitude);
                }
                //考虑10号线等双向的不同
                for(int column = 3; column < columns; column++) {
                    Vertex newStation = findExist(vertices, sheet.getCell(0, column+columns-6).getContents());//初始化为“站名”
                    String startTime = sheet.getCell(column, column+columns-6).getContents();//初始化为“时间”
                    for(int row = column+columns-5; row < rows; row++) {
                        Vertex sucStation = findExist(vertices, sheet.getCell(0, row).getContents());
                        String endTime = sheet.getCell(column, row).getContents();
                        if(endTime.equals("--"))//不停站的情况
                            continue;
                        addEdge(newStation, sucStation, line, startTime, endTime);
                        newStation = sucStation;
                        startTime = endTime;
                        System.out.println(newStation.getName()+ " " + startTime);
                    }
                }

            }
        }catch (BiffException | IOException e) {
            e.printStackTrace();
        }
    }

    //加入站点或者返回已加入站点
    public static Vertex exist(ArrayList<Vertex> vertices, String station,double longitude, double latitude) {
        for (Vertex vertex : vertices) {
            if (vertex.getName().equals(station))
                return vertex;
        }
        Vertex vertex = new Vertex(station,longitude,latitude);
        vertices.add(vertex);
        return  vertex;
    }
    //返回节点
    public static Vertex findExist(ArrayList<Vertex> vertices, String station) {
        for (Vertex vertex : vertices) {
            if (vertex.getName().equals(station))
                return vertex;
        }
        return null;
    }

    //加边
    public static void addEdge(Vertex start, Vertex end, String line, String startTime, String endTime) {
        int cost = getCost(startTime, endTime);
        Edge edge = new Edge(line, cost);
        start.getAdj().add(end);
        start.getEdges().add(edge);
        end.getAdj().add(start);
        end.getEdges().add(edge);
    }
    //计算时间差
    public static int getCost(String start, String end) {
        int length1 = start.length();
        int length2 = end.length();
        int minute = (int)end.charAt(length2 - 1) - (int)start.charAt(length1 - 1);
        int ten = (int)end.charAt(length2 - 2) - (int)start.charAt(length1 - 2);
        int hour = (int)end.charAt(length2 - 4) - (int)start.charAt(length1 - 4);
        hour = hour >= 0? hour : hour + 4;
        return hour * 60 + ten * 10 + minute;
    }
}




