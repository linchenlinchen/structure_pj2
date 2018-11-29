package com.company;

public class Edge {
    private String line;
    private int cost;
    public Edge(String line,int cost) {
        this.line = line;
        this.cost = cost;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
